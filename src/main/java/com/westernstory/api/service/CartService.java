package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.CartDao;
import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.model.CartModel;
import com.westernstory.api.model.CommodityImageModel;
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
public class CartService {
    @Autowired
    private CartDao cartDao = null;
    @Autowired
    private CommodityDao commodityDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 购物车列表
     * @param userId userId
     * @return List<CartModel>
     * @throws ServiceException
     */
    public List<CartModel> list(Long userId) throws ServiceException {
        try {
            List<CartModel> list = cartDao.list(userId);
            for (CartModel model : list) {
                // 商品缩略图
                CommodityImageModel thumbnail = commodityDao.getThumbnail(model.getId());
                if (thumbnail != null) {
                    model.setCommodityThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
                // TODO discount
                model.setDiscount(0f);
            }
            return cartDao.list(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 添加到购物车
     * @param userId userId
     * @param cid cid
     * @param total total
     * @param info info
     * @param addressId addressId
     * @return id
     * @throws ServiceException
     */
    public Long add(Long userId, Long cid, Integer total, String info, Long addressId) throws ServiceException {
        try {
            CartModel model = cartDao.getByUidCid(userId, cid);
            if (model != null) {
                throw new ServiceException("购物车中已存在该商品");
            }
            CartModel cart = new CartModel();
            cart.setIsActive(true);
            cart.setCommodityId(cid);
            cart.setInfo(info);
            cart.setTotal(total);
            cart.setUserId(userId);
            cart.setAddressId(addressId);
            cartDao.add(cart);
            return cart.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 修改购物车
     * @param userId userId
     * @param cid  cid
     * @param total total
     * @param info info
     * @param addressId addressId
     */
    public void update(Long userId, Long cid, Integer total, String info, Long addressId)  throws ServiceException {
        try {
            CartModel model = cartDao.getByUidCid(userId, cid);
            if (model == null) {
                throw new ServiceException("购物车中不存在该商品");
            }
            model.setInfo(info);
            model.setTotal(total);
            model.setAddressId(addressId);
            cartDao.update(model);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 删除购物车某件商品
     * @param id id
     * @throws ServiceException
     */
    public void remove(Long id) throws ServiceException {
        try {
            cartDao.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
    /**
     * 清空购物车
     * @param userId userId
     * @throws ServiceException
     */
    public void removeAll(Long userId) throws ServiceException {
        try {
            cartDao.removeAll(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
