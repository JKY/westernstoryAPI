package com.westernstory.api.controller;

import com.westernstory.api.service.CommodityService;
import com.westernstory.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/commodity")
public class CommodityCtrl {

    @Autowired
    private CommodityService commodityService = null;
    /**
     * 根据类别获取商品列表
     * @return json
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody Response categorylist(@RequestParam(value = "categoryId", required = true) Integer categoryId,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Integer start = (page - 1) * limit;

            return new Response(true, commodityService.get(categoryId, start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 关键字查询商品
     * @param keyword keyword
     * @param page page
     * @param limit limit
     * @return json
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody Response search(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Integer start = (page - 1) * limit;
            return new Response(true, commodityService.getByKeyword(keyword, start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 探索最新商品
     * @return json
     */
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public @ResponseBody Response categorylist(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Integer start = (page - 1) * limit;

            return new Response(true, commodityService.getLatest(start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 探索最新商品
     * @return json
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public @ResponseBody Response detail(@RequestParam(value = "id", required = true) Long id) {
        try {
            return new Response(true, commodityService.getDetail(id));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
