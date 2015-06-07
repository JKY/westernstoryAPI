package com.westernstory.api.controller;

import com.westernstory.api.model.TicketModel;
import com.westernstory.api.service.TicketService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/ticket")
public class TicketCtrl {

    @Autowired
    private TicketService ticketService = null;
    /**
     * 优惠券列表
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit,
                                       @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Integer start = (page - 1) * limit;

            return new Response(true, ticketService.list(keyword, start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 我的优惠券列表
     * @return json
     */
    @RequestMapping(value = "/mylist", method = RequestMethod.GET)
    public @ResponseBody Response myList(@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer limit,
                                       @RequestParam(value = "userId", required = true) Long userId) {
        try {
            if (page == null) {
                page = 1;
            }
            if (limit == null) {
                limit = 10;
            }
            Integer start = (page - 1) * limit;

            return new Response(true, ticketService.getMyList(userId, start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 获取优惠券
     * @return json
     */
    @RequestMapping(value = "/gain", method = RequestMethod.POST)
    public @ResponseBody Response gain(@RequestParam(value = "id", required = true) Long id,
                                       @RequestParam(value = "userId", required = true) Long userId) {
        try {
            ticketService.doGainTicket(id, userId);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
