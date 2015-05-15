package com.westernstory.api.controller;

import com.westernstory.api.service.OrderService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.WsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/order")
public class OrderCtrl {

    @Autowired
    private OrderService orderService = null;
    /**
     * 订单列表
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(HttpServletRequest request,
                                       @RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit) {

        String userId = request.getParameter("userId");
        if(WsUtil.isEmpty(userId)) {
            return new Response(false, "invalid params");
        }
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        int start = (page - 1) * limit;

        try {
            return new Response(true, orderService.list(Long.valueOf(userId), start, limit));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 订单详情
     * @param id id
     * @return json
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public @ResponseBody Response detail(@RequestParam(value = "id", required = true) Integer id) {

        try {
            return new Response(true, orderService.getById(id));
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
