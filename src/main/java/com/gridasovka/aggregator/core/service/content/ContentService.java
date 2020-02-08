package com.gridasovka.aggregator.core.service.content;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {

    public Page<ContentItem> findAllByOrderByIdDesc(Pageable pageable);

    public Iterable<ContentItem> updateContentForSubscription(Subscription subscription, Iterable<ContentItem> contentItems);
}
