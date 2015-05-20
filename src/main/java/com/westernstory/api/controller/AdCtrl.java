package com.westernstory.api.controller;

import com.westernstory.api.service.AdService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/ad")
public class AdCtrl {

    @Autowired
    private AdService adService = null;
    /**
     * 获取最新开机广告
     * @return json
     */
    @RequestMapping(value = "/launch", method = RequestMethod.GET)
    public @ResponseBody Response getLaunch() {
        try {
            return new Response(true, adService.getLaunch());
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
