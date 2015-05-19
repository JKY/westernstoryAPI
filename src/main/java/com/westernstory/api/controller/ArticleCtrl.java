package com.westernstory.api.controller;

import com.westernstory.api.service.ArticleService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/article")
public class ArticleCtrl {

    @Autowired
    private ArticleService articleService = null;
    /**
     * 获取最新开机广告
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(@RequestParam(value = "categoryId", required = true) Long categoryId,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        int start = (page - 1) * limit;

        try {
            return new Response(true, articleService.list(categoryId, start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
