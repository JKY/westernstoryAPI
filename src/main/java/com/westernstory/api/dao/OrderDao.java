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
    List<OrderModel> list(@Param("userId") Long userId, @Param("start") Integer start, @Param("limit") Integer limit) throws Exception;

    /**
     * 根据id获取订单
     * @param id id
     * @return OrderModel
     * @throws Exception
     */
    OrderModel getById(Integer id) throws Exception;

    /**
     * 处理中的订单数据量(排除已签收和已取消的订单)
     * @param userId userId
     * @return Integer
     * @throws Exception
     */
    Integer countProcessingOrders(@Param("userId") Long userId) throws Exception;

    /**
     * 生成订单
     * @param model model
     * @throws Exception
     */
    void add(OrderModel model) throws Exception;
    /**
     * 取消订单
     * @param id id
     * @throws Exception
     */
    void cancel(@Param("id") Long id, @Param("reason") String reason) throws Exception;

    /**
     * 根据用户id获取order总数
     * @param userId userId
     * @return long
     * @throws Exception
     */
    Long count(@Param("userId") Long userId) throws Exception;

    /**
     * 更新订单状态
     * @param id
     * @param status
     * @throws Exception
     */
    void updateStatus(@Param("id") Long id, @Param("status") String status) throws Exception;


    /**
     * 标记订单为已付款
     * @param id
     * @throws Exception
     */
    void paid(@Param("id") Long id) throws Exception;
}
