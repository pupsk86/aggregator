package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.dao.subscription.SubscriptionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class SubscriptionController {

    private SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/subscriptions")
    public ModelAndView list(Model model) {
        return new ModelAndView("views/subscriptions", Map.of("subscriptionList", subscriptionRepository.findAll()));
    }

    @GetMapping("/subscriptions/delete/{subscriptionId}")
    public ModelAndView confirmDelete(@PathVariable("subscriptionId") Long subscriptionId) {
        return new ModelAndView("views/subscription-delete", Map.of("subscriptionId", subscriptionId));
    }

    @PostMapping("/subscriptions/delete/{subscriptionId}")
    public ModelAndView delete(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
        return new ModelAndView("redirect:/subscriptions");
    }

}
