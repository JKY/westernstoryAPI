package com.westernstory.api.dao;

import com.westernstory.api.model.TicketModel;
import com.westernstory.api.model.UserTicketModel;
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

    /**
     * 根据关键字查询总数
     * @param keyword keyword
     * @return long
     * @throws Exception
     */
    Long count(@Param("keyword")String keyword) throws Exception;

    /**
     * 通过user id 获取总数
     * @param userId userId
     * @return long
     * @throws Exception
     */
    Long countByUser(@Param("userId")Long userId) throws Exception;

    /**
     * 通过id、userId 获取优惠券
     * @param ticketId ticketId
     * @param userId userId
     * @return UserTicketModel
     * @throws Exception
     */
    UserTicketModel getUserTicket(@Param("ticketId")Long ticketId, @Param("userId")Long userId) throws Exception;

    /**
     * 领取优惠券
     * @param userTicketModel userTicketModel
     * @throws Exception
     */
    int addUserTicket(UserTicketModel userTicketModel) throws Exception;

    /**
     * 获取我的优惠券详情
     * @param id id
     * @return UserTicketModel
     * @throws Exception
     */
    UserTicketModel getMyTicketDetail(Long id) throws Exception;

    /**
     * 通过userId、ticketId获取用户的优惠券，并且更新为已经使用
     * @param userId userId
     * @param ticketId ticketId
     * @throws Exception
     */
    void updateUseTicket(@Param("userId")Long userId, @Param("ticketId")Long ticketId) throws Exception;

    String getTicketPassword(@Param("ticketId")Long ticketId) throws Exception;
}
