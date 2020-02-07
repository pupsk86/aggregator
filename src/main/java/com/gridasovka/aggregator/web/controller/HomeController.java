package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import com.gridasovka.aggregator.dao.contentitem.ContentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private ContentItemRepository contentItemRepository;

    @GetMapping("/")
    public ModelAndView index(Pageable pageable) {
        Page<ContentItem> contentItemsPage = contentItemRepository.findAll(pageable);
        return new ModelAndView("views/home", Map.of("contentItemsPage", contentItemsPage));
    }
}
