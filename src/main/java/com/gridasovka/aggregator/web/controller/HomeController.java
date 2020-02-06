package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.contentitem.ContentItemRepository;
import com.gridasovka.aggregator.web.exception.ContentItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {

    private ContentItemRepository contentItemRepository;

    public HomeController(ContentItemRepository contentItemRepository) {
        this.contentItemRepository = contentItemRepository;
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("views/home", Map.of("contentItems", contentItemRepository.findAll()));
    }

    @GetMapping("/content-item/{contentItemId}")
    public ModelAndView initNewVisitForm(@PathVariable("contentItemId") Long contentItemId) {
        ContentItem contentItem = contentItemRepository
            .findById(contentItemId)
            .orElseThrow(() -> new ContentItemNotFoundException());

        return new ModelAndView("views/content-item", Map.of("contentItem", contentItem));
    }
}
