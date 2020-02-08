package com.gridasovka.aggregator.web.controller;

import com.gridasovka.aggregator.core.service.content.ContentService;
import com.gridasovka.aggregator.dao.contentitem.ContentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/")
    public ModelAndView index(Pageable pageable, @RequestParam(value="query", required=false, defaultValue="") String query) {
        Page<ContentItem> contentItemsPage = StringUtils.hasText(query) ?
            contentService.search(pageable, query) :
            contentService.getAll(pageable);
        return new ModelAndView("views/home", Map.of(
            "contentItemsPage", contentItemsPage,
            "query", query
        ));
    }
}
