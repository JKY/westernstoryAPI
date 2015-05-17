package com.westernstory.api.service;

import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.model.CommodityModel;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CommodityService {
    @Autowired
    private CommodityDao commodityDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 根据类别获取商品列表
     * @param categoryId categoryId
     * @param start start
     * @param start start
     * @return List<CommodityModel>
     * @throws ServiceException
     */
    public List<CommodityModel> get(Integer categoryId, Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.get(categoryId, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 关键字查询商品
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     */
    public List<CommodityModel> getByKeyword(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.getByKeyword(keyword, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取最新商品
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     */
    public  List<CommodityModel> getLatest(Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.getLatest(start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
