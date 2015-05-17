package com.westernstory.api.dao;

import com.westernstory.api.model.TicketModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface TicketDao {
    /**
     * 优惠券列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<TicketModel> list(@Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 获取ticket
     * @param ticketId ticketId
     * @param userId userId
     * @throws Exception
     */
    void gain(@Param("ticketId")Long ticketId, @Param("userId")Long userId) throws Exception;

    /**
     * 获取优惠券领取数量
     * @param id id
     * @throws Exception
     */
    Integer getCount(@Param("id")Long id) throws Exception;

    /**
     * 通过id获取ticket
     * @param id id
     * @return TicketModel
     * @throws Exception
     */
    TicketModel getById(@Param("id")Long id) throws Exception;
}
