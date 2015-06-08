package com.westernstory.api.dao;

import com.westernstory.api.model.CartModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface CartDao {
    /**
     * 购物车列表
     * @param userId userId
     * @return List<CartModel>
     * @throws Exception
     */
    List<CartModel> list(@Param("userId")Long userId) throws Exception;

    /**
     * 添加到购物车
     * @param cart cart
     * @throws Exception
     */
    void add(CartModel cart) throws Exception;

    /**
     * 修改购物车
     * @param model model
     * @throws Exception
     */
    void update(CartModel model) throws Exception;

    /**
     * 通过uid、cid获取购物车商品
     * @param userId userId
     * @param cid cid
     * @return CartModel
     * @throws Exception
     */
    CartModel getByUidCid(@Param("userId")Long userId, @Param("commodityId")Long cid) throws Exception;

    /**
     * 删除购物车某件商品
     * @param id id
     * @throws Exception
     */
    void remove(@Param("id")Long id) throws Exception;

    /**
     * 清空购物车
     * @param userId userId
     * @throws Exception
     */
    void removeAll(@Param("userId") Long userId) throws Exception;

    /**
     * 通过id 获取购物车model
     * @param id id
     * @return CartModel
     * @throws Exception
     */
    CartModel getById(@Param("id")Long id) throws Exception;
}
