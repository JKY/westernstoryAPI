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
     * 根据uid标记消息已读（批量）
     * @param userId userId
     * @throws Exception
     */
    void readByUid(Long userId) throws Exception;

    /**
     * 删除消息
     * @param id id
     * @throws Exception
     */
    void remove(@Param("id")Long id) throws Exception;
    /**
     * 获取未读消息
     * @param userId userId
     * @return list
     * @throws Exception
     */
    List<MsgModel> getUnreadMsgs(@Param("userId")Long userId) throws Exception;

    /**
     * 获取未读消息数量
     * @param userId userId
     * @return Integer
     * @throws Exception
     */
    Integer countUnreadMsgs(@Param("userId")Long userId) throws Exception;

    /**
     * 根据用户id查询总消息数
     * @param userId userId
     * @return long
     * @throws Exception
     */
    Long count(@Param("userId")Long userId) throws Exception;
}
