layout 'layout.tpl',
    pageTitle: 'Home',
    section: 'Home',
    pageContent: contents {
        hr()
        div(class: 'input-group mb-3') {
            input(type: 'text', class: 'form-control')
            div(class: 'input-group-append') {
                button(class: 'btn btn-outline-secondary', type: 'button', 'Search')

            }

        }
        hr()
        ul(class: 'list-group list-group-flush') {
            contentItemsPage.content.each { contentItem ->
                li(class: 'list-group-item') {
                    a(href: "/content-item/$contentItem.id", "$contentItem.title")
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
