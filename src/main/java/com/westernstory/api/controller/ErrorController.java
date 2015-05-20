package com.westernstory.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ErrorController {
	
	@RequestMapping("/404")
    public String e404() {
		return "404";
	}
	
	@RequestMapping("/500")
    public String e500() {
//		request.setAttribute("e", "网站开了小差！");
		return "500";
	}
}
