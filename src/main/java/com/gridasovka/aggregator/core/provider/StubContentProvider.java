package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class StubContentProvider implements ContentProvider {

    @Override
    public Iterable<ContentItem> getContent(Subscription subscription) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(30));
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return List.of(
                new ContentItem(subscription,"guid1", "link1", "title1", "description1"),
                new ContentItem(subscription,"guid2", "link2", "title2", "description2"),
                new ContentItem(subscription,"guid3", "link3", "title3", "description3"),
                new ContentItem(subscription,"guid4", "link4", "title4", "description4"),
                new ContentItem(subscription,"guid5", "link5", "title5", "description5"),
                new ContentItem(subscription,"guid6", "link6", "title6", "description6"),
                new ContentItem(subscription,"guid7", "link7", "title7", "description7")
        );
    }
}
