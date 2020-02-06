layout 'layout.tpl',
    pageTitle: 'Error',
    pageContent: contents {
        h2("Error $status")
        p(timestamp)
        p(path)
        p(message)
        p(exception)
        pre(trace)
    }