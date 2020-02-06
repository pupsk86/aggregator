layout 'layout.tpl',
    pageTitle: 'Home',
    pageContent: contents {
        div(class: 'input-group mb-3') {
            input(type: 'text', class: 'form-control')
            div(class: 'input-group-append') {
                button(class: 'btn btn-outline-secondary', type: 'button', 'Search')

            }

        }

        ul(class: 'list-group list-group-flush') {
            contentItems.each { contentItem ->
                li(class: 'list-group-item') {
                    a(href:"/content-item/$contentItem.id", "$contentItem.title")
                }
            }
         }
    }
