package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.AdDao;
import com.westernstory.api.model.AdModel;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AdService {
    @Autowired
    private AdDao adDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 获取最新开机广告
     * @return AdModel
     * @throws ServiceException
     */
    public AdModel getLaunch() throws ServiceException {
        try {
            AdModel adModel = adDao.getLaunch();
            if (adModel != null) {
                adModel.setImage(Config.URL_UPLOAD + adModel.getImage());
            }
            return adModel;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
