package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.*;
import com.westernstory.api.model.*;
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
public class OrderService {
    @Autowired
    private OrderDao orderDao = null;
    @Autowired
    private CartDao cartDao = null;
    @Autowired
    private SpecDao specDao = null;
    @Autowired
    private SkuDao skuDao = null;
    @Autowired
    private CommodityDao commodityDao = null;
    @Autowired
    private AddressDao addressDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 订单列表
     * @param userId userId
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public Map<String, Object> list(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            List<OrderModel> orders = orderDao.list(userId, start, limit);
            for (OrderModel order : orders) {
                String infoStr = order.getInfo();
                if(!WsUtil.isEmpty(infoStr)) {
                    List<Long> info = WsUtil.getInfoList(infoStr);
                    List<SpecEntryModel> entries = specDao.getEntriesBySpecInfo(info);
                    order.setSelectedSpec(entries);
                }
                // 商品图片
                CommodityImageModel thumbnail = commodityDao.getThumbnail(order.getCommodityId());
                if (thumbnail != null) {
                    order.setCommodityThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
            }

            map.put("items", orders);
            map.put("count", orderDao.count(userId));

            return map;
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
    public OrderModel getDetail(Integer id) throws ServiceException {
        try {
            OrderModel order = orderDao.getById(id);
            if (order == null) {
                throw new ServiceException("未找到该订单");
            }
            String info = order.getInfo();

            if (!WsUtil.isEmpty(info)) {

                List<SpecEntryModel> entries = specDao.getEntriesBySpecInfo(WsUtil.getInfoList(info));
                order.setSelectedSpec(entries);
            }

            // 商品图片
            List<CommodityImageModel> images = commodityDao.getImages(order.getCommodityId());
            for (CommodityImageModel image : images) {
                image.setImage(Config.URL_STATIC + image.getImage());
            }
            order.setImages(images);

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 购买，生成订单
     * @throws ServiceException
     */
    public void add(OrderModel order) throws ServiceException {
        try {
            Long cid = order.getCommodityId();
            // 商品是否存在
            CommodityModel commodityModel = commodityDao.getById(cid);
            if(commodityModel == null) {
                throw new ServiceException("商品未找到");
            }
            // 获取address
            AddressModel address = addressDao.getById(order.getAddressId());
            if(address == null) {
                throw new ServiceException("未找到寄送地址");
            }
            order.setAddress(address.getAddress());

            // TODO 获取discount
            Float discount = 0f;
            order.setDiscount(discount);

            // 获取info
            String info = order.getInfo();
            ///////////////////// 验证数量 //////////////////////
            List<SKUModel> skus = skuDao.getByCommodityId(cid);
            SKUModel sku = WsUtil.getSku(skus, info);
            if(sku == null) {
                throw new ServiceException("错误的规格信息");
            }

            Integer left = sku.getTotal() - sku.getBuys();
            if(left <= 0) {
                throw new ServiceException("对不起，库存数量超限了");
            }
            if(order.getTotal() > left) {
                throw new ServiceException("对不起，库存不足了");
            }

            order.setInfo(info);
            order.setNumber(WsUtil.getUniqNumber(order.getUserId()));
            order.setIsPaid(false); // TODO
            order.setStatus(OrderModel.STATUS_ADD);
            orderDao.add(order);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
    /**
     * 从购物车中购买
     * @param userId userId
     * @throws ServiceException
     */
    public void addFromCart(Long userId) throws ServiceException {
        try {
            List<CartModel> list = cartDao.list(userId);
            for (CartModel cart : list) {
                OrderModel order = new OrderModel();
                order.setInfo(cart.getInfo());
                order.setTotal(cart.getTotal());
                order.setUserId(cart.getUserId());
                order.setCommodityId(cart.getCommodityId());
                order.setAddressId(cart.getAddressId());
                order.setComment(cart.getComment());
                order.setDiscount(cart.getDiscount());
                order.setPrice(cart.getPrice());

                this.add(order);
            }
            // 清空购物车
            cartDao.removeAll(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 取消订单
     * @param id id
     * @param reason reason
     * @throws ServiceException
     */
    public void doCancel(Long id, String reason) throws ServiceException {
        try {
            orderDao.cancel(id, reason);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
