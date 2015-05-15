package com.westernstory.api.controller;

import com.westernstory.api.model.UserInfoModel;
import com.westernstory.api.service.UserInfoService;
import com.westernstory.api.util.Md5;
import com.westernstory.api.util.Response;
import com.westernstory.api.util.WsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        } catch (Exception e) {
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
//            String agent = WsUtil.getDeviceAgent(request);

            UserInfoModel userinfo = userInfoService.add(userName, password, nickName, ip);
            return new Response(true, userinfo);
        } catch (Exception e) {
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
            if(!password1.equals(password2)) {
                return new Response(false, "两次密码输入不一致");
            }
            userInfoService.updatePassword(Long.valueOf(idString), password1);
            return new Response(true, "");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
