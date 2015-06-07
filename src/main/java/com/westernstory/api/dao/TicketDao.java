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
    List<TicketModel> list(@Param("keyword")String keyword, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 我的优惠券列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List<TicketModel>
     * @throws Exception
     */
    List<TicketModel> getMyList(@Param("userId")Long userId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

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

    /**
     * 获取未被使用的优惠券的数量
     * @param userId userId
     * @return Integer
     * @throws Exception
     */
    Integer countUnusedTickets(@Param("userId")Long userId) throws Exception;
}
