package com.gridasovka.aggregator.core.service.content;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.contentitem.ContentItemRepository;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentItemRepository contentItemRepository;

    @Override
    public Page<ContentItem> getAll(Pageable pageable) {
        return contentItemRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Page<ContentItem> search(Pageable pageable, String query) {
        return contentItemRepository.findByTitleContainingIgnoreCaseOrderByIdDesc(pageable, query);
    }

    @Override
    public synchronized Iterable<ContentItem> updateContentForSubscription(Subscription subscription, Iterable<ContentItemDto> contentItems) {
        List<String> existedContentGuids = new ArrayList<>();
        contentItemRepository
                .findAllBySubscriptionId(subscription.getId())
                .forEach(contentItem -> existedContentGuids.add(contentItem.getGuid()));

        List<ContentItem> newContentItems = new ArrayList<>();
        contentItems.forEach(providedContentItem -> {
            if (!existedContentGuids.contains(providedContentItem.getGuid())) {
                newContentItems.add(new ContentItem(
                    subscription,
                    providedContentItem.getGuid(),
                    providedContentItem.getLink(),
                    providedContentItem.getTitle(),
                    providedContentItem.getDescription()
                ));
            }
        });

        return contentItemRepository.saveAll(newContentItems);
    }
}
