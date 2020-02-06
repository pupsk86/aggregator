layout 'layout.tpl',
    pageTitle: 'Subscriptions',
    section: 'Subscriptions',
    pageContent: contents {
        nav{
            ol(class: 'breadcrumb') {
                li(class: 'breadcrumb-item') {
                    a(href: '/', 'Home')
                }
                li(class: 'breadcrumb-item active', 'Subscriptions')
            }
        }
        table(class: 'table') {
            thead {
                tr {
                    th(scope: 'col', '#')
                    th(scope: 'col', 'Title')
                    th(scope: 'col', 'Url')
                    th(scope: 'col', 'Actions')
                }
            }
            tbody {
                subscriptionList.each { subscription ->
                    tr {
                        th(scope: 'row', subscription.id)
                        td(subscription.title)
                        td(subscription.url)
                        td {
                            div(class: 'btn-group', role: 'group') {
                                a(href: "$spring.requestUri/edit/$subscription.id", class: 'btn btn-sm btn-outline-primary', 'Edit')
                                a(href: "$spring.requestUri/delete/$subscription.id", class: 'btn btn-sm btn-outline-danger', 'Delete')
                            }
                        }
                    }
                }
            }
        }
    }