package com.westernstory.api.dao;

import com.westernstory.api.model.UserInfoModel;
import org.apache.ibatis.annotations.Param;

// Created by fedor on 15/5/13.
public interface UserInfoDao {

    /**
     * 获取登陆用户、验证
     * @param userName userName
     * @param password password
     * @return AdminModel
     * @throws Exception
     */
    public UserInfoModel getLoginUser(@Param("userName") String userName, @Param("password") String password) throws Exception;

    /**
     * 新增用户
     * @param userInfoModel userInfoModel
     * @return id
     * @throws Exception
     */
    Long add(UserInfoModel userInfoModel) throws Exception;

    /**
     * 通过用户名查找用户
     * @param userName userName
     * @return UserInfoModel
     * @throws Exception
     */
    UserInfoModel getByUserName(@Param("userName") String userName) throws Exception;

    /**
     * 更新最近登录信息
     * @param userinfo userinfo
     * @throws Exception
     */
    void updateLastLogin(UserInfoModel userinfo) throws Exception;

    /**
     * 修改密码
     * @param id id
     * @param password password
     * @throws Exception
     */
    void updatePassword(@Param("id")Long id, @Param("password")String password) throws Exception;

    /**
     * 通过用户密码
     * @param id id
     * @return UserInfoModel
     * @throws Exception
     */
    String getPassword(Long id) throws Exception;
}
