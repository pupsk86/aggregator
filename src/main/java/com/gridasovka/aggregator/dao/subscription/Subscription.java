package com.gridasovka.aggregator.dao.subscription;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private Long reindexDelayInMillis;

    @NotNull
    @NotBlank
    private String contentProviderGuid;

    @ElementCollection(fetch = FetchType.EAGER)
    public Map<String, String> contentProviderParameters;

    public Subscription() {}

    public boolean isNew() {
        return this.id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getReindexDelayInMillis() {
        return reindexDelayInMillis;
    }

    public void setReindexDelayInMillis(Long reindexDelayInMillis) {
        this.reindexDelayInMillis = reindexDelayInMillis;
    }

    public String getContentProviderGuid() {
        return contentProviderGuid;
    }

    public void setContentProviderGuid(String contentProviderGuid) {
        this.contentProviderGuid = contentProviderGuid;
    }

    public Map<String, String> getContentProviderParameters() {
        return contentProviderParameters;
    }

    public void setContentProviderParameters(Map<String, String> contentProviderParameters) {
        this.contentProviderParameters = contentProviderParameters;
    }
}
