package com.gridasovka.aggregator.core.service.subscription;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import com.gridasovka.aggregator.core.service.content.ContentService;
import com.gridasovka.aggregator.core.service.provider.ProviderService;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import com.gridasovka.aggregator.dao.subscription.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private TaskScheduler taskScheduler;

    final private Map<Long, ScheduledFuture> scheduledReindexingTasks = new ConcurrentHashMap<>();

    @Override
    public Iterable<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        cancelReindexing(id);
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription save(Subscription subscription) {
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        scheduleReindexing(savedSubscription);
        return savedSubscription;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public void scheduleReindexing() {
        subscriptionRepository.findAll()
            .forEach(this::scheduleReindexing);
    }

    @Override
    public void scheduleReindexing(Subscription subscription) {
        synchronized(scheduledReindexingTasks) {
            cancelReindexing(subscription.getId());

            ContentProvider provider = providerService.getProviderForSubscription(subscription).orElse(null);
            if (provider == null) {
                //TODO: Write err to subscription info
                logger.error("Suitable provider was not found for subscription");
                return;
            }

            ScheduledFuture scheduledFuture = taskScheduler.scheduleWithFixedDelay(
                    () -> {
                        logger.info("Scheduled task is running");
                        try {
                            Iterable<ContentItemDto> providedContentItems = provider.getContent(subscription.getContentProviderParameters());
                            contentService.updateContentForSubscription(subscription, providedContentItems);
                        } catch (Exception ex) {
                            logger.error("Scheduled task is failed", ex);
                        }
                        logger.info("Scheduled task is done");
                    },
                    new Date(),
                    subscription.getReindexDelayInMillis()
            );

            scheduledReindexingTasks.put(subscription.getId(), scheduledFuture);
        }
    }

    private void cancelReindexing(Long subscriptionId) {
        synchronized(scheduledReindexingTasks) {
            ScheduledFuture scheduledTask = scheduledReindexingTasks.get(subscriptionId);
            if (scheduledTask != null) {
                scheduledTask.cancel(true);
                scheduledReindexingTasks.remove(subscriptionId);
            }
        }
    }
}
