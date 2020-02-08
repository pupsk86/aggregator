package com.gridasovka.aggregator.core.listener;

import com.gridasovka.aggregator.core.service.subscription.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    Logger logger = LoggerFactory.getLogger(ApplicationReadyListener.class);

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        subscriptionService.scheduleReindexing();
    }
}
