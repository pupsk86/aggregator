contentProvidersParametersDescription.get(subscription.contentProviderGuid)?.each { contentProviderParameterDescription ->
    def inputId = "contentProviderParameters[$contentProviderParameterDescription.key]"
    div(class: 'form-group') {
        label(for: inputId , contentProviderParameterDescription.value)
        input(type: 'text', class: 'form-control', id: inputId, name: inputId, value: subscription?.contentProviderParameters?.get(contentProviderParameterDescription.key))
    }
}