package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class StubContentProvider implements ContentProvider {

    @Override
    public String getName() {
        return "Stub Content Provider";
    }

    @Override
    public List<ContentItemDto> getContent(Map<String, String> parameters) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(30));
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return List.of(
                new ContentItemDto("guid1", "link1", "title1", "description1"),
                new ContentItemDto("guid2", "link2", "title2", "description2"),
                new ContentItemDto("guid3", "link3", "title3", "description3"),
                new ContentItemDto("guid4", "link4", "title4", "description4"),
                new ContentItemDto("guid5", "link5", "title5", "description5"),
                new ContentItemDto("guid6", "link6", "title6", "description6"),
                new ContentItemDto("guid7", "link7", "title7", "description7")
        );
    }
}
