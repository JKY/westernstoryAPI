package com.westernstory.api.service;

import com.westernstory.api.dao.MsgDao;
import com.westernstory.api.model.MsgModel;
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
public class MsgService {
    @Autowired
    private MsgDao msgDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 消息列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public List<MsgModel> list(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            return msgDao.list(userId, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 标记为已读
     * @param id id
     * @throws ServiceException
     */
    public void updateRead(Long id) throws ServiceException {
        try {
            msgDao.updateRead(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    public void remove(Long id) throws ServiceException {
        try {
            msgDao.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
