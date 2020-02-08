package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.core.provider.ContentProvider;
import com.gridasovka.aggregator.core.service.content.ContentService;
import com.gridasovka.aggregator.core.service.provider.ProviderService;
import com.gridasovka.aggregator.dao.subscription.Subscription;
import com.gridasovka.aggregator.core.service.subscription.SubscriptionService;
import com.gridasovka.aggregator.web.exception.SubscriptionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SubscriptionController {

    final private String VIEW_LIST = "views/subscriptions";
    final private String VIEW_CREATE_OR_EDIT = "views/subscription_create_or_edit";
    final private String VIEW_DELETE = "views/subscription_delete";

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ProviderService providerService;

    @GetMapping("/subscriptions")
    public ModelAndView list(Model model) {
        return new ModelAndView(VIEW_LIST, Map.of(
            "subscriptionList", subscriptionService.findAll()
        ));
    }

    @GetMapping("/subscriptions/{subscriptionId}/delete")
    public ModelAndView delete(@PathVariable("subscriptionId") Long subscriptionId) {
        return new ModelAndView(VIEW_DELETE, Map.of(
            "subscriptionId", subscriptionId
        ));
    }

    @PostMapping("/subscriptions/{subscriptionId}/delete")
    public ModelAndView handleDelete(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.deleteById(subscriptionId);
        return new ModelAndView("redirect:/subscriptions");
    }

    @GetMapping("/subscriptions/create")
    public ModelAndView create() {
        return new ModelAndView(VIEW_CREATE_OR_EDIT, Map.of(
            "subscription", new Subscription()
        ));
    }

    @PostMapping("/subscriptions/create")
    public ModelAndView handleCreate(@Valid Subscription subscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(VIEW_CREATE_OR_EDIT, Map.of(
                "subscription", subscription,
                "bindingResult", bindingResult
            ));
        }
        subscriptionService.save(subscription);
        return new ModelAndView("redirect:/subscriptions");
    }

    @GetMapping("/subscriptions/{subscriptionId}/edit")
    public ModelAndView edit(@PathVariable("subscriptionId") Long subscriptionId) {
        Subscription subscription = subscriptionService
            .findById(subscriptionId)
            .orElseThrow(SubscriptionNotFoundException::new);
        return new ModelAndView(VIEW_CREATE_OR_EDIT, Map.of(
            "subscription", subscription
        ));
    }

    @PostMapping("/subscriptions/{subscriptionId}/edit")
    public ModelAndView handleEdit(@Valid Subscription subscription, BindingResult bindingResult, @PathVariable("subscriptionId") Long subscriptionId) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(VIEW_CREATE_OR_EDIT, Map.of(
                "subscription", subscription,
                "bindingResult", bindingResult
            ));
        }
        subscription.setId(subscriptionId);
        subscriptionService.save(subscription);
        return new ModelAndView("redirect:/subscriptions");
    }

    @PostMapping("/ajax/subscriptions/content-provider-parameters")
    public ModelAndView ajaxContentProviderParameters(Subscription subscription) {
        return new ModelAndView("views/subscription_content_provider_parameters", Map.of(
            "subscription", subscription
        ));
    }

    @ModelAttribute("contentProvidersParametersDescription")
    private Map<String, Map<String, String>> getContentProvidersParametersDescription() {
        return providerService.getProviders().stream()
            .collect(Collectors.toMap(contentProvider -> contentProvider.getGuid(), ContentProvider::getParametersDescription));
    }

    @ModelAttribute("contentProviderGuidOptions")
    private Map<String, String> getContentProviderGuidOptions() {
        return providerService.getProviders().stream()
            .collect(Collectors.toMap(contentProvider -> contentProvider.getGuid(), ContentProvider::getName));
    }
}
