package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.TicketDao;
import com.westernstory.api.model.TicketModel;
import com.westernstory.api.model.UserTicketModel;
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
public class TicketService {
    @Autowired
    private TicketDao ticketDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 优惠券列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public Map<String, Object> list(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            List<TicketModel> list = ticketDao.list(keyword, start, limit);
            for (TicketModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
            }
            map.put("items", list);
            map.put("count", ticketDao.count(keyword));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 我的优惠券列表
     * @param userId userId
     * @param start userId
     * @param limit limit
     * @return
     */
    public  Map<String, Object> getMyList(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            List<TicketModel> list = ticketDao.getMyList(userId, start, limit);
            for (TicketModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
            }
            map.put("items", list);
            map.put("count", ticketDao.countByUser(userId));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取ticket
     * @param ticketId ticketId
     * @param userId userId
     * @throws ServiceException
     */
    public void doGainTicket(Long ticketId, Long userId) throws ServiceException {
        try {
            UserTicketModel userTicket = ticketDao.getUserTicket(ticketId, userId);
            if (userTicket != null) {
                throw new ServiceException("您已经领取了此优惠券");
            }

            TicketModel ticket = ticketDao.getById(ticketId);
            Integer count = ticketDao.getCount(ticketId);
            if(count >= ticket.getTotal()) {
                throw new ServiceException("优惠券已领完");
            }
            ticketDao.gain(ticketId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
