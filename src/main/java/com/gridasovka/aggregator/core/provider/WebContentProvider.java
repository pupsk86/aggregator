package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public abstract class WebContentProvider implements ContentProvider {

    abstract protected String getUrl(Map<String, String> parameters);

    abstract protected List<ContentItemDto> processResponse(HttpUriRequest request, HttpResponse response, InputStream content, Map<String, String> parameters) throws IOException;

    protected RequestConfig getRequestConfig(Map<String, String> parameters) {
        return RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    }

    @Override
    public List<ContentItemDto> getContent(Map<String, String> parameters) {
        String url = getUrl(parameters);

        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpGet request = new HttpGet(url);
            request.setConfig(getRequestConfig(parameters));
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                return processResponse(request, response, stream, parameters);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    protected String getParameterOrThrow(Map<String, String> parameters, String paramKey) {
        String paramValue = parameters.get(paramKey);
        Assert.hasText(paramValue, String.format("Mandatory parameter \"%s\" is missing", getParametersDescription().get(paramKey)));
        return paramValue;
    }
}
