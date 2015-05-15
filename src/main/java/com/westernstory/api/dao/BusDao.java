package com.westernstory.api.dao;

import com.westernstory.api.model.BusEntryModel;
import com.westernstory.api.model.BusModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface BusDao {
    /**
     * 获取班车列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<BusModel> list(@Param("start") Integer start, @Param("limit") Integer limit) throws Exception;
    /**
     * 获取班车详情
     * @param busId busId
     * @return List
     * @throws Exception
     */
    List<BusEntryModel> getDetailById(@Param("busId") Long busId) throws Exception;

    /**
     * 关键字查询班车
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<BusModel> getByKeyword(@Param("keyword") String keyword, @Param("start") Integer start, @Param("limit") Integer limit) throws Exception;
}
