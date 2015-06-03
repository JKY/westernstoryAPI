package com.westernstory.api.controller;

import com.westernstory.api.model.CartModel;
import com.westernstory.api.service.CartService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/cart")
public class CartCtrl {

    @Autowired
    private CartService cartService = null;
    /**
     * 购物车列表
     * @return json
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Response list(@RequestParam(value = "userId", required = true) Long userId) {
        try {
            return new Response(true, cartService.list(userId));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 添加到购物车
     * @return json
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Response additem(@ModelAttribute("cart") CartModel cart) {
        try {
            return new Response(true, cartService.add(cart));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 添加到购物车
     * @return json
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody Response update(@ModelAttribute("cart") CartModel cart) {
        try {
            cartService.update(cart);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 删除购物车某件商品
     * @return json
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody Response remove(@RequestParam(value = "id", required = true) Long id) {
        try {
            cartService.remove(id);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 清空购物车
     * @return json
     */
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public @ResponseBody Response clean(@RequestParam(value = "userId", required = true) Long userId) {
        try {
            cartService.removeAll(userId);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
