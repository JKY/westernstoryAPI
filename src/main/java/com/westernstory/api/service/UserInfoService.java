package com.westernstory.api.service;

import com.westernstory.api.dao.*;
import com.westernstory.api.model.UserInfoModel;
import com.westernstory.api.util.Md5;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao = null;
    @Autowired
    private MsgDao msgDao = null;
    @Autowired
    private OrderDao orderDao = null;
    @Autowired
    private TicketDao ticketDao = null;
    @Autowired
    private CartDao cartDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 登录
     * @param userName userName
     * @param password password
     * @param ip ip
     * @return UserInfoModel
     * @throws com.westernstory.api.util.ServiceException
     */
    public UserInfoModel doLogin(String userName, String password, String ip) throws ServiceException {
        UserInfoModel userinfo;
        try {
            userinfo = userInfoDao.getLoginUser(userName, password);
            if (userinfo == null) {
                throw new ServiceException("用户名或密码错误");
            }
            if (!userinfo.getIsActive()) {
                throw new ServiceException("用户已被删除");
            }
            if (userinfo.getIsLocked()) {
                throw new ServiceException("用户已被锁定");
            }
            // 更新最近登录信息
            userinfo.setLastLoginIp(ip);
            userinfo.setLastLoginTime(System.currentTimeMillis());
            userInfoDao.updateLastLogin(userinfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
        return userinfo;
    }

    /**
     * 注册
     * @param userName userName
     * @param password password
     * @param nickName nickName
     * @param ip ip
     * @return id
     * @throws com.westernstory.api.util.ServiceException
     */
    public UserInfoModel add(String userName, String password, String nickName, String ip) throws ServiceException {

        try {
            UserInfoModel tmp = userInfoDao.getByUserName(userName);
            if(tmp != null) {
                throw new ServiceException("用户名已经存在");
            }
            UserInfoModel userinfo = new UserInfoModel();
            userinfo.setUserName(userName.toLowerCase());
            userinfo.setMobile(userinfo.getUserName()); // mobile number
            userinfo.setPassword(Md5.toMD5(password.toLowerCase()));
            userinfo.setNickName(nickName);
            userinfo.setLastLoginIp(ip);

            userInfoDao.add(userinfo);
            return userinfo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 修改密码
     * @param id id
     * @param oldpass 旧密码
     * @param newpass 新密码
     * @throws com.westernstory.api.util.ServiceException
     */
    public void updatePassword(Long id, String oldpass, String newpass) throws ServiceException {
        try {
            String pass = userInfoDao.getPassword(id);
            if (Md5.toMD5(oldpass.toLowerCase()).equals(pass)) {
                userInfoDao.updatePassword(id, Md5.toMD5(newpass));
            } else {
                throw new ServiceException("原密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 个人中心badge 各种数量值
     * @param userId userId
     */
    public Map<String, Integer> getBadge(Long userId) throws ServiceException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            if (userId != 0L) {
                // 消息
                map.put("msg", msgDao.countUnreadMsgs(userId));
                // 订单
                map.put("order", orderDao.countProcessingOrders(userId));
                // 优惠券
                map.put("ticket", ticketDao.countUnusedTickets(userId));
                // 购物车
                map.put("cart", cartDao.countByUser(userId));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 生成验证码
     * @param mobile mobile
     * @return vcode
     */
    public String doMakeVcode(String mobile) throws ServiceException {
        try {
            // make vcode
            List<Integer> numbers = new ArrayList<Integer>();
            for(int i = 0; i < 10; i++){
                numbers.add(i);
            }
            Collections.shuffle(numbers);
            String vcode = "";
            for(int i = 0; i < 4; i++){
                vcode += numbers.get(i).toString();
            }
            userInfoDao.makeVcode(mobile, vcode);
            return vcode;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 根据用户名验证码获取用户
     * @param mobile mobile
     * @param vcode vcode
     * @return UserInfoModel
     * @throws com.westernstory.api.util.ServiceException
     */
    public UserInfoModel getUserByMobileVcode(String mobile, String vcode) throws ServiceException {

        try {
            UserInfoModel userinfo = userInfoDao.getUserByMobileVcode(mobile, vcode);
            if(userinfo == null) {
                throw new ServiceException("验证码错误");
            }
            return userinfo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
