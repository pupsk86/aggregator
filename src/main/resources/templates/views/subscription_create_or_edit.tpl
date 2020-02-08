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
                label(for: 'reindexDelayInMillis', 'Reindex delay (ms)')
                input(type: 'text', class: 'form-control' + (bindingResult?.hasFieldErrors('reindexDelayInMillis') ? ' is-invalid' : ''), id: 'reindexDelayInMillis', name: 'reindexDelayInMillis', value: subscription.reindexDelayInMillis)
                div(class: 'invalid-feedback', "" + bindingResult?.getFieldError('reindexDelayInMillis')?.getDefaultMessage())
            }
            div(class: 'form-group') {
                label(for: 'contentProviderGuid' , 'Content provider')
                select(class: 'form-control' + (bindingResult?.hasFieldErrors('contentProviderGuid') ? ' is-invalid' : ''), id: 'contentProviderGuid', name: 'contentProviderGuid') {
                    option(value: '', '')
                    contentProviderGuidOptions.each { contentProviderGuidOption ->
                        if (subscription.contentProviderGuid == contentProviderGuidOption.key) {
                            option(value: contentProviderGuidOption.key, selected: 'selected', contentProviderGuidOption.value)
                        } else {
                            option(value: contentProviderGuidOption.key, contentProviderGuidOption.value)
                        }
                    }
                }
                div(class: 'invalid-feedback', "" + bindingResult?.getFieldError('contentProviderGuid')?.getDefaultMessage())
            }
            div(id: 'subscriptionContentProviderParameters') {
                include template: 'views/subscription_content_provider_parameters.tpl'
            }
            button(type: 'submit', class: 'btn btn-success') {
                span(subscription.new ? 'Create' : 'Update')
                span(class: 'spinner spinner-grow spinner-grow-sm', style: 'display: none', '')
            }
        }
        script('''
            (() => {
                let $form = $('form');
                let $submitButton = $form.children('button[type=submit]');
                let $submitButtonSpinner = $submitButton.children('.spinner');
                let $subscriptionContentProviderParameters = $('#subscriptionContentProviderParameters');
                let $contentProviderGuid = $('#contentProviderGuid');
                $('#contentProviderGuid').change(() => {
                    $submitButton.prop('disabled', true);
                    $submitButtonSpinner.show();
                    axios.post('/ajax/subscriptions/content-provider-parameters', new FormData($form[0]))
                        .then(function (response) {
                            $subscriptionContentProviderParameters.html(response.data);
                         })
                         .catch(function (error) {
                             console.log(error);
                          })
                         .then(function () {
                             $submitButton.prop('disabled', false);
                             $submitButtonSpinner.hide();
                         });
                });
            })();
        ''')
    }