package com.westernstory.api.dao;

import com.westernstory.api.model.CommonPhoneEntryModel;
import com.westernstory.api.model.CommonPhoneModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface CommonPhoneDao {
    /**
     * 获取常用电话分类列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<CommonPhoneModel> listCategories(@Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 根据categoryId获取常用电话列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<CommonPhoneEntryModel> listPhones(@Param("categoryId")Long categoryId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;
}
