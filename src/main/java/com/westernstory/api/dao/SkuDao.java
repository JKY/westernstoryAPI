package com.westernstory.api.dao;

import com.westernstory.api.model.SKUModel;

import java.util.List;

// Created by fedor on 15/5/13.
public interface SkuDao {

    /**
     * 添加SKU
     * @param model model
     * @throws Exception
     */
    void add(SKUModel model) throws Exception;

    /**
     * 通过商品id获取sku
     * @param cid cid
     * @return List<SKUModel>
     * @throws Exception
     */
    List<SKUModel> getByCommodityId(Long cid) throws Exception;
}
