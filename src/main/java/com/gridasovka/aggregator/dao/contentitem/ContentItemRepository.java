package com.gridasovka.aggregator.dao.contentitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContentItemRepository extends PagingAndSortingRepository<ContentItem, Long> {

    public Iterable<ContentItem> findAllBySubscriptionId(Long subscriptionId);

    public Page<ContentItem> findAllByOrderByIdDesc(Pageable pageable);

    public Page<ContentItem> findByTitleContainingIgnoreCaseOrderByIdDesc(Pageable pageable, String title);
}
