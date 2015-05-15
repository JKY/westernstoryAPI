package com.westernstory.api.service;

import com.westernstory.api.dao.UserInfoDao;
import com.westernstory.api.model.UserInfoModel;
import com.westernstory.api.util.Md5;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 登录
     * @param userName userName
     * @param password password
     * @param ip ip
     * @return UserInfoModel
     * @throws ServiceException
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
     * @throws ServiceException
     */
    public UserInfoModel add(String userName, String password, String nickName, String ip) throws ServiceException {

        try {
            UserInfoModel tmp = userInfoDao.getByUserName(userName);
            if(tmp != null) {
                throw new ServiceException("用户名已经存在");
            }
            UserInfoModel userinfo = new UserInfoModel();
            userinfo.setUserName(userName.toLowerCase());
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
     * @param password password
     * @throws ServiceException
     */
    public void updatePassword(Long id, String password) throws ServiceException {
        try {
            userInfoDao.updatePassword(id, Md5.toMD5(password));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
