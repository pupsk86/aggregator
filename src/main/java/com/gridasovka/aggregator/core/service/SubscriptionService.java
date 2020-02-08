package com.gridasovka.aggregator.core.service;

import com.gridasovka.aggregator.dao.subscription.Subscription;

import java.util.Optional;

public interface SubscriptionService {

    public Iterable<Subscription> findAll();

    public void deleteById(Long id);

    public Subscription save(Subscription subscription);

    public Optional<Subscription> findById(Long id);

    public void scheduleSubscriptionsReIndexing();

    public void scheduleSubscriptionReIndexing(Subscription subscription);
}
