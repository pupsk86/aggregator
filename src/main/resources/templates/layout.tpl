yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title('Aggregator - ' + pageTitle)
        link(rel: 'stylesheet', href: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css')
        script('', type:'text/javascript', src: 'https://code.jquery.com/jquery-3.4.1.slim.min.js')
        script('', type:'text/javascript', src: 'https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js')
        script('', type:'text/javascript', src: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js')
        script('', type:'text/javascript', src: 'https://unpkg.com/axios/dist/axios.min.js')
        style('''
            .card {
                overflow: hidden;
            }
            .card img {
                max-width: 100%;
            }
        ''')
    }
    body {
        nav(class: 'navbar navbar-expand-lg navbar-light bg-light sticky-top') {
            a(class: 'navbar-brand', href: '/', 'Aggregator')
            button(class: 'navbar-toggler', type: 'button', 'data-toggle': 'collapse', 'data-target': '#navbarSupportedContent') {
                span(class: 'navbar-toggler-icon')
            }
            div(class: 'collapse navbar-collapse', id: 'navbarSupportedContent') {
                ul(class: 'navbar-nav mr-auto') {
                    li(class: 'nav-item' + (section == 'Home' ? ' active' : '')) {
                        a(class: 'nav-link', href: '/', 'Home')
                    }
                    li(class: 'nav-item' + (section == 'Subscriptions' ? ' active' : '')) {
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