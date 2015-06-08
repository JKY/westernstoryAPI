package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.MsgDao;
import com.westernstory.api.model.MsgModel;
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
    public Map<String, Object> list(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<MsgModel> list = msgDao.list(userId, start, limit);
            for(MsgModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
            }
            map.put("items", list);
            map.put("count", msgDao.count(userId));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 根据uid标记消息已读
     * @param userId userId
     */
    public void updateReadByUid(Long userId) throws ServiceException {
        try {
            msgDao.readByUid(userId);
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
