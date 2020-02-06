layout 'layout.tpl',
    pageTitle: "Content Item #$contentItem.id",
    section: 'Home',
    pageContent: contents {
        nav{
            ol(class: 'breadcrumb') {
                li(class: 'breadcrumb-item') {
                    a(href: '/', 'Home')
                }
                li(class: 'breadcrumb-item active', contentItem.title)
            }
        }
        h2(contentItem.title)
        p(contentItem.body)
    }