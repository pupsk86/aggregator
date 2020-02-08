package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ContentProvider {

    public String getName();

    default public String getGuid() {
        return getClass().getCanonicalName();
    }

    default public Map<String, String> getParametersDescription() {
        return Collections.emptyMap();
    }

    public List<ContentItemDto> getContent(Map<String, String> parameters);
}
