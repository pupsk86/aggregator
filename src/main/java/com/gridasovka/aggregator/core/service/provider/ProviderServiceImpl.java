package com.gridasovka.aggregator.core.service.provider;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {


    @Autowired
    private List<ContentProvider> providers;

    @Override
    public List<ContentProvider> getProviders() {
        return providers;
    }

    @Override
    public Optional<ContentProvider> getProviderForSubscription(Subscription subscription) {
        return providers.stream()
                .filter(p -> p.getClass().getCanonicalName().equals(subscription.getContentProvider())).findFirst();
    }
}
