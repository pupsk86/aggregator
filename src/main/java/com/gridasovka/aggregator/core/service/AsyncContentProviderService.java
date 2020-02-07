package com.gridasovka.aggregator.core.service;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncContentProviderService {

    @Async
    public CompletableFuture<Iterable<ContentItem>> getContent(ContentProvider provider) {
        return CompletableFuture.completedFuture(provider.getContent());
    }
}
