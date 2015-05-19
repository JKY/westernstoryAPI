package com.westernstory.api.controller;

import com.westernstory.api.service.BusService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/tool/bus")
public class BusCtrl {

    @Autowired
    private BusService busService = null;

    /**
     * 获取班车列表
     * @param page page
     * @param limit limit
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
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

            return new Response(true, busService.list(start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 获取班车详情
     * @param busId busId
     * @return json
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public @ResponseBody Response categorylist(@RequestParam(value = "id", required = true) Long busId) {
        try {
            return new Response(true, busService.getDetailById(busId));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 关键字查询班车
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
            return new Response(true, busService.getByKeyword(keyword, start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
