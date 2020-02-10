layout 'layout.tpl',
    pageTitle: 'Home',
    section: 'Home',
    pageContent: contents {
        hr()
        form(method: 'GET', action: spring.requestUri) {
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
                def uriBuilder = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                def currPageNumber = contentItemsPage.number
                def prevPageNumber = Math.max(currPageNumber - 1, 0)
                def lastPageNumber = Math.max(contentItemsPage.totalPages - 1, 0)
                def fromPageNumber = Math.max(currPageNumber - 5 + Math.min(lastPageNumber - currPageNumber - 5, 0), 0)
                def toPageNumber = Math.min(currPageNumber + (10 - (currPageNumber - fromPageNumber)), lastPageNumber)

                li(class: 'page-item' + (contentItemsPage.hasPrevious() ? '' : ' disabled')) {
                    a(class: 'page-link', href: uriBuilder.fromCurrentRequest().replaceQueryParam("page", prevPageNumber).build(), 'Previous')
                }
                (fromPageNumber..toPageNumber).each { n ->
                    li(class: 'page-item' + (n == currPageNumber ? ' active' : '')) {
                        a(class: 'page-link', href: uriBuilder.fromCurrentRequest().replaceQueryParam("page", n).build(), n)
                    }
                }
                li(class: 'page-item' + (contentItemsPage.hasNext() ? '' : ' disabled')) {
                    a(class: 'page-link', href: uriBuilder.fromCurrentRequest().replaceQueryParam("page", currPageNumber + 1).build(), 'Next')
                }
            }
        }
    }
