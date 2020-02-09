layout 'layout.tpl',
    pageTitle: 'Home',
    section: 'Home',
    pageContent: contents {
        hr()
        form(method: 'POST', action: spring.requestUri) {
            div(class: 'input-group mb-3') {
                input(type: 'text', class: 'form-control', name: 'query', value: query)
                div(class: 'input-group-append') {
                    button(class: 'btn btn-outline-secondary', type: 'submit', 'Search')
                }
            }
        }
        hr()
        contentItemsPage.content.each { contentItem ->
            div(class: 'card mb-3') {
                div(class: 'card-body') {
                    h5(class: 'card-title') {
                        a(href: contentItem.link, target: '_blank', contentItem.title)
                    }
                    h6(class: 'card-subtitle mb-2 text-muted', contentItem.subscription.title)
                    p(class: 'card-text', contentItem.description)
                }
            }
        }
        hr()
        nav{
            ul(class: 'pagination justify-content-center') {
                def currPageNumber = contentItemsPage.number
                def prevPageNumber = currPageNumber - 1

                li(class: 'page-item' + (contentItemsPage.hasPrevious() ? '' : ' disabled')) {
                    a(class: 'page-link', href: (prevPageNumber > 0 ? "/?page=$prevPageNumber" : '/'), 'Previous')
                }
                (0..Math.max(contentItemsPage.totalPages - 1, 0)).each { n ->
                    li(class: 'page-item' + (n == currPageNumber ? ' active' : '')) {
                        a(class: 'page-link', href: (n > 0 ? "/?page=$n" : '/'), n)
                    }
                }
                li(class: 'page-item' + (contentItemsPage.hasNext() ? '' : ' disabled')) {
                    a(class: 'page-link', href: "/?page=${currPageNumber + 1}", 'Next')
                }
            }
        }
    }
