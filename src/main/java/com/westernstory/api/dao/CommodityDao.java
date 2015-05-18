package com.westernstory.api.dao;

import com.westernstory.api.model.CommodityModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface CommodityDao {
    /**
     * 根据类别获取商品列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> get(@Param("categoryId")Integer categoryId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 关键字查询商品
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> getByKeyword(@Param("keyword")String keyword, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 探索最新商品
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> getLatest(@Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 通过id获取商品详情
     * @param id id
     * @return CommodityModel
     * @throws Exception
     */
    CommodityModel getById(@Param("id")Long id) throws Exception;
}
