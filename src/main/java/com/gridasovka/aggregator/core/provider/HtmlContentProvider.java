package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HtmlContentProvider extends WebContentProvider {

    final private String PARAM_KEY_URL = "url";
    final private String PARAM_KEY_ARTICLE_SELECTOR = "articleSelector";
    final private String PARAM_KEY_LINK_SELECTOR = "linkSelector";
    final private String PARAM_KEY_TITLE_SELECTOR = "titleSelector";
    final private String PARAM_KEY_DESCRIPTION_SELECTOR = "descriptionSelector";

    private Logger logger = LoggerFactory.getLogger(HtmlContentProvider.class);

    @Override
    public String getName() {
        return "Universal html parser";
    }

    @Override
    public Map<String, String> getParametersDescription() {
        return Map.of(
            PARAM_KEY_URL, "Url",
            PARAM_KEY_ARTICLE_SELECTOR, "Article selector",
            PARAM_KEY_LINK_SELECTOR, "Link selector",
            PARAM_KEY_TITLE_SELECTOR, "Title selector",
            PARAM_KEY_DESCRIPTION_SELECTOR, "Description selector"
        );
    }

    @Override
    protected String getUrl(Map<String, String> parameters) {
        return getParameterOrThrow(parameters, PARAM_KEY_URL);
    }

    @Override
    protected List<ContentItemDto> processResponse(HttpUriRequest request, HttpResponse response, InputStream content, Map<String, String> parameters) throws IOException {
        String articleSelector = getParameterOrThrow(parameters, PARAM_KEY_ARTICLE_SELECTOR);
        String linkSelector = getParameterOrThrow(parameters, PARAM_KEY_LINK_SELECTOR);
        String titleSelector = getParameterOrThrow(parameters, PARAM_KEY_TITLE_SELECTOR);
        String descriptionSelector = getParameterOrThrow(parameters, PARAM_KEY_DESCRIPTION_SELECTOR);

        Document document = Jsoup.parse(content, ContentType.getOrDefault(response.getEntity()).getCharset().toString(), request.getURI().toString());
        List<ContentItemDto> contentItems = document.select(articleSelector).stream()
                .map(element -> {

                    Element linkElement = element.select(linkSelector).first();
                    Element titleElement = element.select(titleSelector).first();
                    Element descriptionElement = element.select(descriptionSelector).first();

                    String link = (linkElement != null ? linkElement.attr("href") : null);
                    URL absoluteLink = null;
                    try {
                        absoluteLink = new URL(request.getURI().toURL(), link);
                    } catch (MalformedURLException ex) {
                        logger.error("Can not parse link", ex);
                    }
                    return new ContentItemDto(
                            absoluteLink != null ? absoluteLink.toString() : null,
                            absoluteLink != null ? absoluteLink.toString() : null,
                            titleElement != null ? titleElement.text() : null,
                            descriptionElement != null ? descriptionElement.text() : null
                    );
                })
                .filter(contentItem -> contentItem.getGuid() != null && !contentItem.getGuid().isBlank())
                .collect(Collectors.toList());
        Collections.reverse(contentItems);
        return contentItems;
    }
}
