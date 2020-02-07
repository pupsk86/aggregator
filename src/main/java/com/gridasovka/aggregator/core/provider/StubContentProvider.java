package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StubContentProvider implements ContentProvider {
    @Override
    public Iterable<ContentItem> getContent() {
        return List.of(
                new ContentItem("title1", "body1"),
                new ContentItem("title2", "body2"),
                new ContentItem("title3", "body3"),
                new ContentItem("title4", "body4"),
                new ContentItem("title5", "body5"),
                new ContentItem("title6", "body6"),
                new ContentItem("title7", "body7")
        );
    }
}
