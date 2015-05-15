package com.westernstory.api.service;

import com.westernstory.api.dao.OrderDao;
import com.westernstory.api.model.OrderModel;
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
public class OrderService {
    @Autowired
    private OrderDao orderDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 订单列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public List<OrderModel> list(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            return orderDao.list(userId, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 订单详情
     * @param id id
     * @return OrderModel
     * @throws ServiceException
     */
    public OrderModel getById(Integer id) throws ServiceException {
        try {
            OrderModel order = orderDao.getById(id);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
