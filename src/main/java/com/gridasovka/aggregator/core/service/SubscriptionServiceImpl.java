package com.gridasovka.aggregator.core.service;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import com.gridasovka.aggregator.dao.subscription.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private List<ContentProvider> providers;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

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
    }
}
