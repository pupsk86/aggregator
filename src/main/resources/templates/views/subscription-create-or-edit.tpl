layout 'layout.tpl',
    pageTitle: 'Subscriptions > ' + (subscription.new ? 'Create' : 'Edit') + ' Subscription',
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
                li(class: 'breadcrumb-item active', (subscription.new ? 'Create' : 'Edit') + ' Subscription')
            }
        }
        hr()
        h2((subscription.new ? 'Create' : 'Edit')  + ' Subscription')
        form(method: 'POST', action: spring.requestUri) {
            div(class: 'form-group') {
                label(for: 'title', 'Title')
                input(type: 'text', class: 'form-control' + (bindingResult?.hasFieldErrors('title') ? ' is-invalid' : ''), id: 'title', name: 'title', value: subscription.title)
                div(class: 'invalid-feedback', "" + bindingResult?.getFieldError('title')?.getDefaultMessage())
            }
            div(class: 'form-group') {
                label(for: 'url', 'Url')
                input(type: 'text', class: 'form-control' + (bindingResult?.hasFieldErrors('url') ? ' is-invalid' : ''), id: 'url', name: 'url', value: subscription.url)
                div(class: 'invalid-feedback', "" + bindingResult?.getFieldError('url')?.getDefaultMessage())
            }
            button(type: 'submit', class: 'btn btn-success', subscription.new ? 'Create' : 'Update')
        }
    }