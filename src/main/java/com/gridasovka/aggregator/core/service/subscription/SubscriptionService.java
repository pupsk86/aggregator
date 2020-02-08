package com.gridasovka.aggregator.core.service.subscription;

import com.gridasovka.aggregator.dao.subscription.Subscription;

import java.util.Optional;

public interface SubscriptionService {

    public Iterable<Subscription> findAll();

    public void deleteById(Long id);

    public Subscription save(Subscription subscription);

    public Optional<Subscription> findById(Long id);

    public void scheduleReindexing();

    public void scheduleReindexing(Subscription subscription);
}
