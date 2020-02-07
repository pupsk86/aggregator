package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;

public interface ContentProvider {
    public Iterable<ContentItem> getContent();
}
