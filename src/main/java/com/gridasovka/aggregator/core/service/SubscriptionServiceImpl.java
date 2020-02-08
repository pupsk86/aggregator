package com.gridasovka.aggregator.core.service;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.contentitem.ContentItemRepository;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import com.gridasovka.aggregator.dao.subscription.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
    private AsyncContentProviderService asyncIndexService;

    private Map<Long, CompletableFuture> reIndexTasks = new HashMap<>();

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
        Subscription subscription1 = subscriptionRepository.save(subscription);
        reIndexSubscription(subscription1);
        return subscription1;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public void reIndexSubscription(Subscription subscription) {
        logger.info("providers=" + providers);
        ContentProvider provider = getProviderForSubscription(subscription);

        synchronized (reIndexTasks) {
            CompletableFuture completableFuture = reIndexTasks.get(subscription.getId());
            logger.info("completableFuture=" + completableFuture);
            if (completableFuture != null) {
                completableFuture.cancel(true);
            }
            CompletableFuture completableFuture1 = asyncIndexService.getContent(subscription, provider).handleAsync((contentItems, throwable) -> {
                if (throwable != null) {
                    logger.error("reIndexSubscription error: ", throwable);
                }
                return CompletableFuture.completedFuture(contentItemRepository.saveAll(contentItems));
            });
            reIndexTasks.put(subscription.getId(), completableFuture1);
        }
    }

    private ContentProvider getProviderForSubscription(Subscription subscription) {
        return providers.stream().filter(p -> p.getClass().getCanonicalName().equals("com.gridasovka.aggregator.core.provider.StubContentProvider")).findFirst().get();
    }
}
