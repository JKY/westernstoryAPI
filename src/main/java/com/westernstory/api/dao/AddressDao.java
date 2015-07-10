package com.westernstory.api.dao;

import com.westernstory.api.model.AdModel;
import com.westernstory.api.model.AddressModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface AddressDao {
    /**
     * 获取最新的开机广告
     * @return AdModel
     * @throws Exception
     */
    AdModel getLastLaunch() throws Exception;
    /**
     * 获取地址列表
     * @param userId userId
     * @return list
     * @throws Exception
     */
    List<AddressModel> list(@Param("userId") Long userId) throws Exception;

    /**
     * 地址列表
     * @param address address
     * @throws Exception
     */
    void add(AddressModel address) throws Exception;

    /**
     * 取消默认地址
     * @param userId userId
     * @throws Exception
     */
    void clearDefault(@Param("userId") Long userId) throws Exception;

    /**
     * 修改地址
     * @param address address
     * @throws Exception
     */
    void update(AddressModel address) throws Exception;

    /**
     * 删除地址
     * @param id id
     * @throws Exception
     */
    void remove(@Param("id") Long id) throws Exception;

    /**
     * 设置为默认地址
     * @param id id
     * @throws Exception
     */
    void setDefault(Long id) throws Exception;

    /**
     * 根据ID获取Address
     * @param id id
     * @return AddressModel
     * @throws Exception
     */
    AddressModel getById(@Param("id") Long id) throws Exception;
}
