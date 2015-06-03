package com.westernstory.api.controller;

import com.westernstory.api.config.Config;
import com.westernstory.api.model.ArticleModel;
import com.westernstory.api.model.DictionaryEntryModel;
import com.westernstory.api.service.ArticleService;
import com.westernstory.api.service.DictionaryService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/article")
public class ArticleCtrl {

    @Autowired
    private ArticleService articleService = null;
    @Autowired
    private DictionaryService dictionaryService = null;

    /**
     * 文章分类列表
     * @return json
     */
    @RequestMapping(value = "/categorylist", method = RequestMethod.GET)
    public @ResponseBody Response categorylist(){

        try {
            List<DictionaryEntryModel> models = dictionaryService.listDictionariesByCode("article_category");

            for (DictionaryEntryModel model : models) {
                model.setIcon(Config.URL_STATIC + model.getIcon());
                if ("1".equals(model.getInfo())) {
                    model.setIsHeadline(true);
                } else {
                    model.setIsHeadline(false);
                }
            }
            return new Response(true, models);
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }


    /**
     * 根据类别获取文章
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(@RequestParam(value = "categoryId", required = false) Long categoryId,
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

    /**
     * 文章详情
     * @param request request
     * @return ftl
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String getArticleDetail(HttpServletRequest request){

        try {
            String id = request.getParameter("id");
            if (WsUtil.isEmpty(id)) {
                return "404";
            } else {
                ArticleModel article = articleService.getDetail(Long.valueOf(id));
                if (article == null) {
                    return "404";
                } else {
                    request.setAttribute("article", article);
                    return "article_detail";
                }
            }
        } catch (ServiceException e) {
            return "500";
        }
    }
}
