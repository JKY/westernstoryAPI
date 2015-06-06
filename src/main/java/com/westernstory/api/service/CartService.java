package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.CartDao;
import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.dao.SkuDao;
import com.westernstory.api.model.CartModel;
import com.westernstory.api.model.CommodityImageModel;
import com.westernstory.api.model.CommodityModel;
import com.westernstory.api.model.SKUModel;
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
    private SkuDao skuDao = null;
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
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 添加到购物车
     * @param cart cart
     * @throws ServiceException
     */
    public Long add(CartModel cart) throws ServiceException {
        try {
            Long cid = cart.getCommodityId();
            CartModel model = cartDao.getByUidCid(cart.getUserId(), cid);
            if (model != null) {
                throw new ServiceException("购物车中已存在该商品");
            }

            String info = cart.getInfo();
            ///////////////////// 验证数量 //////////////////////
            List<SKUModel> skus = skuDao.getByCommodityId(cid);
            SKUModel sku = WsUtil.getSku(skus, info);
            Integer left;
            if (sku != null) {
                left = sku.getTotal() - sku.getBuys();
            } else {
                CommodityModel commodityModel = commodityDao.getById(cid);
                if(commodityModel != null) {
                    Integer tmp = commodityDao.getBuyCountFromNoneSpec(cid);
                    tmp = tmp == null? 0: tmp;
                    left = commodityModel.getTotal() - tmp;
                } else {
                    throw new ServiceException("商品不存在");
                }
            }
            if(left <= 0) {
                throw new ServiceException("对不起，库存不足了");
            }
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
     * @param cart cart
     */
    public void update(CartModel cart)  throws ServiceException {
        try {
            Long cid = cart.getCommodityId();
            CartModel model = cartDao.getByUidCid(cart.getUserId(), cid);
            if (model == null) {
                throw new ServiceException("购物车中不存在该商品");
            }

            String info = cart.getInfo();
            ///////////////////// 验证数量 //////////////////////
            List<SKUModel> skus = skuDao.getByCommodityId(cid);
            SKUModel sku = WsUtil.getSku(skus, info);
            Integer left;
            if (sku != null) {
                left = sku.getTotal() - sku.getBuys();
            } else {
                CommodityModel commodityModel = commodityDao.getById(cid);
                if(commodityModel != null) {
                    Integer tmp = commodityDao.getBuyCountFromNoneSpec(cid);
                    tmp = tmp == null? 0: tmp;
                    left = commodityModel.getTotal() - tmp;
                } else {
                    throw new ServiceException("商品不存在");
                }
            }
            if(left <= 0) {
                throw new ServiceException("对不起，库存不足了");
            }

            cartDao.update(cart);
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
