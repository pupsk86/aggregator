package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RssContentProvider implements ContentProvider {

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
    public List<ContentItemDto> getContent(Map<String, String> parameters) {
        String url = parameters.get(PARAM_KEY_URL);

        Assert.hasText(url, "Mandatory parameter \"Url\" is missing");

        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                InputStream stream = response.getEntity().getContent()) {
                SyndFeed feed = new SyndFeedInput().build(new XmlReader(stream));
                List<SyndEntry> feedEntries = feed.getEntries();
                Collections.reverse(feedEntries);
                return feedEntries.stream()
                    .map(syndEntry -> new ContentItemDto(syndEntry.getUri(), syndEntry.getLink(), syndEntry.getTitle(), syndEntry.getDescription().getValue()))
                    .collect(Collectors.toList());
            }
        } catch (IOException | FeedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
