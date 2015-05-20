package com.westernstory.api.dao;

import com.westernstory.api.model.AdModel;

// Created by fedor on 15/5/13.
public interface AdDao {
    /**
     * 获取最新的开机广告
     * @return AdModel
     * @throws Exception
     */
    AdModel getLaunch() throws Exception;
}
