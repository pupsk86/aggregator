package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.dao.subscription.Subscription;
import com.gridasovka.aggregator.core.service.subscription.SubscriptionService;
import com.gridasovka.aggregator.web.exception.SubscriptionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    public ModelAndView list(Model model) {
        return new ModelAndView("views/subscriptions", Map.of("subscriptionList", subscriptionService.findAll()));
    }

    @GetMapping("/subscriptions/{subscriptionId}/delete")
    public ModelAndView delete(@PathVariable("subscriptionId") Long subscriptionId) {
        return new ModelAndView("views/subscription-delete", Map.of("subscriptionId", subscriptionId));
    }

    @PostMapping("/subscriptions/{subscriptionId}/delete")
    public ModelAndView handleDelete(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.deleteById(subscriptionId);
        return new ModelAndView("redirect:/subscriptions");
    }

    @GetMapping("/subscriptions/create")
    public ModelAndView create() {
        Subscription subscription = new Subscription("", "");
        return new ModelAndView("views/subscription-create-or-edit", Map.of("subscription", subscription));
    }

    @PostMapping("/subscriptions/create")
    public ModelAndView handleCreate(@Valid Subscription subscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("views/subscription-create-or-edit", Map.of("subscription", subscription,"bindingResult", bindingResult));
        }
        subscriptionService.save(subscription);

        return new ModelAndView("redirect:/subscriptions");
    }

    @GetMapping("/subscriptions/{subscriptionId}/edit")
    public ModelAndView edit(@PathVariable("subscriptionId") Long subscriptionId) {
        Subscription subscription = subscriptionService
            .findById(subscriptionId)
            .orElseThrow(SubscriptionNotFoundException::new);
        return new ModelAndView("views/subscription-create-or-edit", Map.of("subscription", subscription));
    }

    @PostMapping("/subscriptions/{subscriptionId}/edit")
    public ModelAndView handleEdit(@Valid Subscription subscription, BindingResult bindingResult, @PathVariable("subscriptionId") Long subscriptionId) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("views/subscription-create-or-edit", Map.of("subscription", subscription, "bindingResult", bindingResult));
        }
        subscription.setId(subscriptionId);
        subscriptionService.save(subscription);
        return new ModelAndView("redirect:/subscriptions");
    }

}
