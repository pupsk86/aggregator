package com.gridasovka.aggregator.core.service.content;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {

    public Page<ContentItem> getAll(Pageable pageable);

    public Page<ContentItem> search(Pageable pageable, String query);

    public Iterable<ContentItem> updateContentForSubscription(Subscription subscription, Iterable<ContentItemDto> contentItems);
}
