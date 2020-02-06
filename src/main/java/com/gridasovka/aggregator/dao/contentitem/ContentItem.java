package com.gridasovka.aggregator.dao.contentitem;

import javax.persistence.*;

@Entity
public class ContentItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String body;

    protected ContentItem() {}

    public ContentItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
