package com.gridasovka.aggregator.core.provider;

import com.gridasovka.aggregator.core.provider.dto.ContentItemDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ContentProvider {

    /**
     * Returns a human-readable name of this provider.
     */
    public String getName();

    /**
     * Returns a Unique id which will be stored in db and used for matching later.
     */
    default public String getGuid() {
        return getClass().getCanonicalName();
    }

    /**
     * Returns a hash table with parameters description where key is parameter id
     * and value is human-readable parameter name.
     */
    default public Map<String, String> getParametersDescription() {
        return Collections.emptyMap();
    }

    /**
     * Returns a collection of ContentItemDto objects which will be stored in db.
     */
    public List<ContentItemDto> getContent(Map<String, String> parameters);
}
