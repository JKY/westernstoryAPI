package com.westernstory.api.controller;

import com.westernstory.api.service.DictionaryService;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// Created by fedor on 15/5/13.
@Controller
@RequestMapping(value = "/dict")
public class DictionaryCtrl {

    @Autowired
    private DictionaryService dictionaryService = null;

    /**
     * 根据code获取 List<DictionaryEntry>
     * @param code code
     * @return josn
     */
    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    public @ResponseBody Response listDictionariesByCode(@PathVariable String code) {
        try {
            return new Response(true, dictionaryService.listDictionariesByCode(code));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
}
