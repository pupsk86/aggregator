package com.gridasovka.aggregator.dao.contentitem;

import com.gridasovka.aggregator.dao.subscription.Subscription;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class ContentItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subscription subscription;

    private String guid;

    private String link;

    private String title;

    @Lob
    private String description;

    protected ContentItem() {}

    public ContentItem(Subscription subscription, String guid, String link, String title, String description) {
        this.subscription = subscription;
        this.guid = guid;
        this.link = link;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Subscription getSubscription() {
        return subscription;
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
