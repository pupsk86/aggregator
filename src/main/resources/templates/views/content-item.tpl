layout 'layout.tpl',
    pageTitle: "Content Item #$contentItem.id",
    pageContent: contents {
        h2(contentItem.title)
        p(contentItem.body)
    }