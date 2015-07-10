package com.westernstory.api.dao;

import com.westernstory.api.model.ChargeModel;


public interface ChargeDao {
    /**
     * 根据 id 获取 Model
     * @param id
     * @return
     * @throws Exception
     */
    ChargeModel getById(String id) throws Exception;

    /**
     * 新增
     * @param chargeModel
     * @return
     * @throws Exception
     */
    Long add(ChargeModel chargeModel) throws Exception;
}
