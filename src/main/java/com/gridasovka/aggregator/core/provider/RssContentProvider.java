package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RssContentProvider extends WebContentProvider {

    private final String PARAM_KEY_URL = "url";

    @Override
    public String getName() {
        return "RSS reader";
    }

    @Override
    public Map<String, String> getParametersDescription() {
        return Map.of(
            PARAM_KEY_URL, "Url"
        );
    }

    @Override
    protected String getUrl(Map<String, String> parameters) {
        return getParameterOrThrow(parameters, PARAM_KEY_URL);
    }

    @Override
    protected List<ContentItemDto> processResponse(HttpUriRequest request, HttpResponse response, InputStream content, Map<String, String> parameters) throws IOException {
        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(content));
            List<SyndEntry> feedEntries = feed.getEntries();
            Collections.reverse(feedEntries);
            return feedEntries.stream()
                .map(syndEntry -> new ContentItemDto(
                        syndEntry.getUri(),
                        syndEntry.getLink(),
                        syndEntry.getTitle(),
                        syndEntry.getDescription().getValue()
                ))
                .collect(Collectors.toList());
        } catch (FeedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
