package com.westernstory.api.controller;

import com.westernstory.api.config.Config;
import com.westernstory.api.model.TicketModel;
import com.westernstory.api.model.UserTicketModel;
import com.westernstory.api.service.TicketService;
import com.westernstory.api.util.Md5;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            ticketService.addUserTicket(id, userId);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 我的优惠券详情
     * @return json
     */
    @RequestMapping(value = "/mydetail", method = RequestMethod.POST)
    public @ResponseBody Response gain(@RequestParam(value = "id", required = true) Long id) {
        try {
            return new Response(true, ticketService.getMyTicketDetail(id));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 优惠券兑换（扫描二维码）
     * @param request request
     * @return ftl
     */
    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    public String scan(HttpServletRequest request) {

        String userIdStr = request.getParameter("uid");
        String ticketIdStr = request.getParameter("tid");
        String token = request.getParameter("token");

        try {

            if(WsUtil.isEmpty(userIdStr) || WsUtil.isEmpty(ticketIdStr) || WsUtil.isEmpty(token)) {
                request.setAttribute("e", "无效的参数");
                return "ticket_identity_result";
            }

            if (!token.equals(Md5.toMD5(ticketIdStr + "#" + userIdStr + "#" + Config.WEB_KEY))) {
                request.setAttribute("e", "验证错误");
                return "ticket_identity_result";
            }

            Long ticketId = Long.valueOf(ticketIdStr);
            Long userId = Long.valueOf(userIdStr);

            String password = ticketService.getTicketPassword(ticketId);
            String cookieValue = WsUtil.getCookie(request, Config.COOKIE_TICKET_IDENTIFY);
            if (cookieValue != null && cookieValue.equals(Md5.toMD5(password))) {
                ticketService.doIdentify(userId, ticketId);
                return "ticket_identity_result";
            } else {
                // 输入密码
                request.setAttribute("uid", userIdStr);
                request.setAttribute("tid", ticketIdStr);
                request.setAttribute("token", token);
                request.setAttribute("ticket", ticketService.getMyTicketDetail(userId, ticketId));
                return "ticket_identity_form";
            }
        } catch (ServiceException e) {
            request.setAttribute("e", e.getMessage());
            return "ticket_identity_result";
        }
    }

    /**
     * 验证优惠券二维码
     * @param request request
     * @return ftl
     */
    @RequestMapping(value = "/identify", method = RequestMethod.POST)
    public String identify(HttpServletRequest request, HttpServletResponse response) {

        String userIdStr = request.getParameter("uid");
        String ticketIdStr = request.getParameter("tid");
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        try {
            if(WsUtil.isEmpty(userIdStr) || WsUtil.isEmpty(ticketIdStr) || WsUtil.isEmpty(token) || WsUtil.isEmpty(password)) {
                request.setAttribute("e", "无效的参数");
                return "ticket_identity_result";
            }

            if (!token.equals(Md5.toMD5(ticketIdStr + "#" + userIdStr + "#" + Config.WEB_KEY))) {
                request.setAttribute("e", "验证错误");
                return "ticket_identity_result";
            }

            Long ticketId = Long.valueOf(ticketIdStr);
            Long userId = Long.valueOf(userIdStr);

            String realPassword = ticketService.getTicketPassword(ticketId);
            if(!password.equals(realPassword)) {
                request.setAttribute("e", "密码不正确");
                return "ticket_identity_result";
            }

            ticketService.doIdentify(userId, ticketId);
            // 存入cookie
            WsUtil.setCookie(Config.COOKIE_TICKET_IDENTIFY, Md5.toMD5(password), response, 3600 * 24);
            return "ticket_identity_result";
        } catch (ServiceException e) {
            request.setAttribute("e", e.getMessage());
            return "ticket_identity_result";
        }
    }
}
