package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.subscription.Subscription;

public interface ContentProvider {
    public Iterable<ContentItem> getContent(Subscription subscription);
}
