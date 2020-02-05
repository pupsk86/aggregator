layout 'views/layout.tpl',
    pageTitle: 'Home',
    pageContent: contents {
        div(class: 'input-group mb-3') {
            input(type: 'text', class: 'form-control')
            div(class: 'input-group-append') {
                button(class: 'btn btn-outline-secondary', type: 'button', 'Search')

            }

        }
        ul(class: 'list-group list-group-flush') {
            li(class: 'list-group-item') {
                a(href:"/news/1", "News #1")
            }
            li(class: 'list-group-item') {
                a(href:"/news/2", "News #2")
            }
         }
    }
