package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.TicketDao;
import com.westernstory.api.model.TicketModel;
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
    public List<TicketModel> list(Integer start, Integer limit) throws ServiceException {
        try {
            List<TicketModel> list = ticketDao.list(start, limit);
            for (TicketModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
            }
            return list;
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
