package com.westernstory.api.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.westernstory.api.config.Config;
import com.westernstory.api.model.UserInfoModel;
import com.westernstory.api.service.UserInfoService;
import com.westernstory.api.util.Md5;
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

/**
 * Created by fedor on 15/5/13.
 */
@Controller
public class UserDoorCtrl {

    @Autowired
    private UserInfoService userInfoService = null;
    /**
     * 登陆
     * @param request request
     * @return json
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Response login(HttpServletRequest request) {

        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            if (userName == null || password == null) {
                return new Response(false, "invalid params");
            }
            String ip = WsUtil.getIpAddr(request);
            UserInfoModel userinfo = userInfoService.doLogin(userName.toLowerCase(), Md5.toMD5(password.toLowerCase()), ip);
            if (userinfo != null) {
                return new Response(true, userinfo);
            }
            return new Response(false, "user not found");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 注册用户
     * @param request request
     * @return json
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Response register(HttpServletRequest request) {

        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String nickName = request.getParameter("nickName");
            if (userName == null || password == null || nickName == null
                    || "".equals(userName) || "".equals(password) || "".equals(nickName)) {
                return new Response(false, "invalid params");
            }

            String ip = WsUtil.getIpAddr(request);
            UserInfoModel userinfo = userInfoService.add(userName, password, nickName, ip);

            userinfo.setPassword(null);
            return new Response(true, userinfo);
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }
    /**
     * 更改密码
     * @param request request
     * @return json
     */
    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public @ResponseBody Response changepass(HttpServletRequest request) {

        try {
            String password1 = request.getParameter("password1");
            String password2 = request.getParameter("password2");
            String idString = request.getParameter("id");
            if (password1 == null || password2 == null || idString == null
                    || "".equals(password1) || "".equals(password2) || "".equals(idString)) {
                return new Response(false, "invalid params");
            }

            userInfoService.updatePassword(Long.valueOf(idString), password1, password2);
            return new Response(true, "ok");
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 个人中心badge 各种数量值
     * @return json
     */
    @RequestMapping(value = "/badge", method = RequestMethod.GET)
    public @ResponseBody Response badge(@RequestParam(value = "userId", required = true) Long userId) {

        try {
            return new Response(true, userInfoService.getBadge(userId));
        } catch (ServiceException e) {
            return new Response(false, e.getMessage());
        }
    }

    /**
     * 获取验证码（找回密码）
     * @return json
     */
    @RequestMapping(value = "/getvcode", method = RequestMethod.POST)
    public @ResponseBody Response getvcode(@RequestParam(value = "mobile", required = true) String mobile) {

        try {
            String vcode = userInfoService.doMakeVcode(mobile);
            HttpResponse httpResponse = Unirest.post(Config.SMS_URL_ROOT)
                    .field("username", Config.SMS_USERNAME)
                    .field("password", Config.SMS_PASSWORD)
                    .field("epid", Config.SMS_EPID)
                    .field("mobile", mobile)
                    .field("message", WsUtil.smsContent("尊敬的用户：您的验证码为 " + vcode))
                            .asString();
            int status = httpResponse.getStatus();

            if (status == 200) {
                Object obj = httpResponse.getBody();

                return new Response(true, obj);
            } else {
                return new Response(false, "system error");
            }
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
