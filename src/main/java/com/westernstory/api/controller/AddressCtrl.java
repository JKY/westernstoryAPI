package com.westernstory.api.controller;

import com.westernstory.api.model.AddressModel;
import com.westernstory.api.service.AddressService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/addr")
public class AddressCtrl {

    @Autowired
    private AddressService addressService = null;
    /**
     * 地址列表
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if(WsUtil.isEmpty(userId)) {
            return new Response(false, "invalid params");
        }
        try {
            return new Response(true, addressService.list(Long.valueOf(userId)));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
    /**
     * 新增地址
     * @return json
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Response add(@ModelAttribute("address") AddressModel address) {
        if(WsUtil.isEmpty(address.getUserId())
                || WsUtil.isEmpty(address.getAddress())
                || WsUtil.isEmpty(address.getMobile())
                || WsUtil.isEmpty(address.getName())) {
            return new Response(false, "invalid params");
        }
        try {
            return new Response(true, addressService.add(address));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
    /**
     * 修改地址
     * @return json
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody Response update(@ModelAttribute("address") AddressModel address) {
        if(WsUtil.isEmpty(address.getId())
                || WsUtil.isEmpty(address.getAddress())
                || WsUtil.isEmpty(address.getMobile())
                || WsUtil.isEmpty(address.getName())
                || WsUtil.isEmpty(address.getIsDefault())) {
            return new Response(false, "invalid params");
        }
        try {
            addressService.update(address);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
    /**
     * 删除地址
     * @return json
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody Response remove(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(WsUtil.isEmpty(id)) {
            return new Response(false, "invalid params");
        }
        try {
            addressService.remove(Long.valueOf(id));
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
    /**
     * 设置为默认地址
     * @return json
     */
    @RequestMapping(value = "/default", method = RequestMethod.POST)
    public @ResponseBody Response setdefault(HttpServletRequest request) {
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");
        if(WsUtil.isEmpty(id) || WsUtil.isEmpty(userId)) {
            return new Response(false, "invalid params");
        }
        try {
            addressService.updateDefault(Long.valueOf(id), Long.valueOf(userId));
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
