package com.gridasovka.aggregator.core.service.provider;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.subscription.Subscription;

import java.util.List;
import java.util.Optional;

public interface ProviderService {

    public List<ContentProvider> getProviders();

    public Optional<ContentProvider> getProviderForSubscription(Subscription subscription);
}
