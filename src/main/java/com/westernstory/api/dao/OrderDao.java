package com.westernstory.api.dao;

import com.westernstory.api.model.OrderModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface OrderDao {
    /**
     * 订单列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List
     * @throws Exception
     */
    List<OrderModel> list(@Param("userId")Long userId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 根据id获取订单
     * @param id id
     * @return OrderModel
     * @throws Exception
     */
    OrderModel getById(Integer id) throws Exception;
}
