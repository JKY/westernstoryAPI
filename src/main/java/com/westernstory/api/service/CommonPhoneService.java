package com.westernstory.api.service;

import com.westernstory.api.dao.CommonPhoneDao;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CommonPhoneService {
    @Autowired
    private CommonPhoneDao commonPhoneDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 获取常用电话分类列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public Map<String, Object> listCategories(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("items", commonPhoneDao.listCategories(keyword, start, limit));
            map.put("count", commonPhoneDao.countCategories(keyword, start, limit));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 根据categoryId获取常用电话列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public  Map<String, Object> listPhones(String keyword, Long categoryId, int start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", commonPhoneDao.listPhones(keyword, categoryId, start, limit));
            map.put("count", commonPhoneDao.countPhones(keyword, categoryId, start, limit));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
