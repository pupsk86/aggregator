package com.gridasovka.aggregator.core.provider.dto;

public class ContentItemDto {

    private String guid;

    private String link;

    private String title;

    private String description;

    public ContentItemDto(String guid, String link, String title, String description) {
        this.guid = guid;
        this.link = link;
        this.title = title;
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
