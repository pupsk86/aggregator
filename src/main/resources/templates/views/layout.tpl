yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title('Aggregator - ' + pageTitle)
        link(rel: 'stylesheet', href: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css')
        script('', type:'text/javascript', src: 'https://code.jquery.com/jquery-3.4.1.slim.min.js')
        script('', type:'text/javascript', src: 'https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js')
        script('', type:'text/javascript', src: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js')
    }
    body {
        nav(class: 'navbar navbar-expand-lg navbar-light bg-light sticky-top') {
            a(class: 'navbar-brand', href: '/', 'Aggregator')
            button(class: 'navbar-toggler', type: 'button', 'data-toggle': 'collapse', 'data-target': '#navbarSupportedContent', 'aria-controls': 'navbarSupportedContent', 'aria-expanded': 'false', 'aria-label': 'Toggle navigation') {
                span(class: 'navbar-toggler-icon')
            }
            div(class: 'collapse navbar-collapse', id: 'navbarSupportedContent') {
                ul(class: 'navbar-nav mr-auto') {
                    li(class: 'nav-item active') {
                        a(class: 'nav-link', href: '/', 'Home')
                    }
                    li(class: 'nav-item') {
                        a(class: 'nav-link', href: '/subscriptions', 'Subscriptions')
                    }
                }
            }
        }
        div(class: 'container') {
            pageContent()
        }
    }
}