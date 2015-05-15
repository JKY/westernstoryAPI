package com.westernstory.api.dao;

import com.westernstory.api.model.MsgModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface MsgDao {
    /**
     * 消息列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<MsgModel> list(@Param("userId")Long userId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 标记为已读
     * @param id id
     * @throws Exception
     */
    void updateRead(@Param("id")Long id) throws Exception;

    /**
     * 删除消息
     * @param id id
     * @throws Exception
     */
    void remove(@Param("id")Long id) throws Exception;
}
