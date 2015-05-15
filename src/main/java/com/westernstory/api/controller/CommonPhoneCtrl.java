package com.westernstory.api.controller;

import com.westernstory.api.service.CommonPhoneService;
import com.westernstory.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/tool/phone")
public class CommonPhoneCtrl {

    @Autowired
    private CommonPhoneService commonPhoneService = null;
    /**
     * 获取电话分类列表
     * @return json
     */
    @RequestMapping(value = "/categorylist", method = RequestMethod.GET)
    public @ResponseBody Response categorylist(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            int start = (page - 1) * limit;

            return new Response(true, commonPhoneService.listCategories(start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 根据categoryId获取常用电话列表
     * @param categoryId categoryId
     * @param page page
     * @param limit limit
     * @return json
     */
    @RequestMapping(value = "/phonelist", method = RequestMethod.GET)
    public @ResponseBody Response phonelist(@RequestParam(value = "categoryId", required = true) Long categoryId,
                                                @RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            int start = (page - 1) * limit;

            return new Response(true, commonPhoneService.listPhones(categoryId, start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
