package com.westernstory.api.controller;

import com.westernstory.api.service.MsgService;
import com.westernstory.api.util.JPush;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
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
@RequestMapping(value = "/msg")
public class MsgCtrl {

    @Autowired
    private MsgService msgService = null;
    /**
     * 消息列表
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
            return new Response(true, msgService.list(Long.valueOf(userId), start, limit));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 标记为已读
     * @param userId userId
     * @return json
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public @ResponseBody Response read(@RequestParam(value = "userId", required = true) Long userId) {

        try {
            msgService.updateReadByUid(userId);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 删除消息
     * @param id id
     * @return json
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody Response remove(@RequestParam(value = "id", required = true) Long id) {
        try {
            msgService.remove(id);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 测试推送消息
     * @param title title
     * @return json
     */
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public @ResponseBody Response push(@RequestParam(value = "title", required = true) String title) {
        JPush.push(title, JPush.TYPE_MSG, 1000L);
        return new Response(true, "ok");
    }
}
