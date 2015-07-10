package com.westernstory.api.service;

import com.westernstory.api.dao.BusDao;
import com.westernstory.api.model.BusEntryModel;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class BusService {
    @Autowired
    private BusDao busDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 获取班车列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws com.westernstory.api.util.ServiceException
     */
    public Map<String, Object> list(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", busDao.list(keyword, start, limit));
            map.put("count", busDao.count(keyword));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取班车详情
     * @param busId busId
     * @return List
     * @throws com.westernstory.api.util.ServiceException
     */
    public List<BusEntryModel> getDetailById(Long busId) throws ServiceException {
        try {
            return busDao.getDetailById(busId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 关键字查询班车
     * @param keyword keyword
     * @param start page
     * @param limit limit
     * @return List
     * @throws com.westernstory.api.util.ServiceException
     */
    public Map<String, Object> getByKeyword(String keyword, Integer start, Integer limit) throws ServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("items", busDao.list(keyword, start, limit));
            map.put("count", busDao.count(keyword));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
