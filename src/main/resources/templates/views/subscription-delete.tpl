layout 'layout.tpl',
    pageTitle: 'Subscriptions > Delete Subscription',
    section: 'Subscriptions',
    pageContent: contents {
        nav{
            ol(class: 'breadcrumb') {
                li(class: 'breadcrumb-item') {
                    a(href: '/', 'Home')
                }
                li(class: 'breadcrumb-item') {
                    a(href: '/subscriptions', 'Subscriptions')
                }
                li(class: 'breadcrumb-item active', 'Delete Subscription')
            }
        }
        hr()
        h2('Delete Subscription')
        p('Are you sure you want to delete the selected subscription?')
        form(method: 'POST', action: spring.requestUri) {
            //TODO: Add csrf token
            button(type: 'submit', class: 'btn btn-danger', 'Delete')
        }
    }