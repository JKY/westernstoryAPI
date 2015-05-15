package com.westernstory.api.service;

import com.westernstory.api.dao.AddressDao;
import com.westernstory.api.model.AddressModel;
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
public class AddressService {
    @Autowired
    private AddressDao addressDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 获取地址列表
     * @param userId userId
     * @return list
     * @throws ServiceException
     */
    public List<AddressModel> list(Long userId) throws ServiceException {
        try {
            return addressDao.list(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 地址列表
     * @param address address
     * @return id
     * @throws ServiceException
     */
    public Long add(AddressModel address) throws ServiceException {
        try {
            List<AddressModel> list = addressDao.list(address.getUserId());
            if (list.size() == 0) {
                address.setIsDefault(true);
            } else {
                address.setIsDefault(false);
            }
            addressDao.add(address);
            return address.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 修改地址
     * @param address address
     * @throws ServiceException
     */
    public void update(AddressModel address) throws ServiceException {
        try {
            addressDao.update(address);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 删除地址
     * @param id id
     * @throws ServiceException
     */
    public void remove(String id) throws ServiceException {
        try {
            addressDao.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 设置为默认地址
     * @param id id
     * @param userId userId
     * @throws ServiceException
     */
    public void updateDefault(Long id, Long userId) throws ServiceException {
        try {
            addressDao.noDefault(userId);
            addressDao.setDefault(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
