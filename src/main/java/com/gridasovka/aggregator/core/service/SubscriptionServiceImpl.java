package com.gridasovka.aggregator.core.service;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.contentitem.ContentItemRepository;
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

    Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private List<ContentProvider> providers;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ContentItemRepository contentItemRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    final private Map<Long, ScheduledFuture> scheduledSubscriptionReIndexingTasks = new ConcurrentHashMap<>();

    @Override
    public Iterable<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription save(Subscription subscription) {
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        scheduleSubscriptionReIndexing(savedSubscription);
        return savedSubscription;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public void scheduleSubscriptionsReIndexing() {
        subscriptionRepository.findAll()
            .forEach(subscription -> scheduleSubscriptionReIndexing(subscription));
    }

    @Override
    public synchronized void scheduleSubscriptionReIndexing(Subscription subscription) {
        ScheduledFuture outdatedTask = scheduledSubscriptionReIndexingTasks.get(subscription.getId());
        if (outdatedTask != null) {
            outdatedTask.cancel(true);
            scheduledSubscriptionReIndexingTasks.remove(subscription.getId());
        }

        ContentProvider provider = getProviderForSubscription(subscription);
        if (provider == null) {
            //TODO: Write err to subscription info
        }

        ScheduledFuture scheduledFuture = taskScheduler.scheduleWithFixedDelay(
                () -> {
                    logger.info("GKA__sceduled task is running");
                    try {
                        contentItemRepository.saveAll(provider.getContent(subscription));
                    } catch (Exception ex) {
                        logger.error("GKA__sceduled task is failed", ex);
                    }
                    logger.info("GKA__sceduled task is done");
                },
                new Date(),
                10 * 1000
        );

        scheduledSubscriptionReIndexingTasks.put(subscription.getId(), scheduledFuture);
    }

    private ContentProvider getProviderForSubscription(Subscription subscription) {
        return providers.stream().filter(p -> p.getClass().getCanonicalName().equals("com.gridasovka.aggregator.core.provider.StubContentProvider")).findFirst().get();
    }
}
