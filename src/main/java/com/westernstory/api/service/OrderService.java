package com.westernstory.api.service;

import com.google.common.reflect.TypeToken;
import com.westernstory.api.config.Config;
import com.westernstory.api.dao.*;
import com.westernstory.api.model.*;
import com.westernstory.api.util.GsonUtil;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao = null;
    @Autowired
    private SpecDao specDao = null;
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
    public List<OrderModel> list(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            List<OrderModel> orders = orderDao.list(userId, start, limit);
            for (OrderModel order : orders) {
                String infoStr = order.getInfo();
                if(!WsUtil.isEmpty(infoStr)) {
                    List<Long> info = WsUtil.getInfoLong(infoStr);
                    List<SpecEntryModel> entries = specDao.getEntriesBySpecInfo(info);
                    order.setSelectedSpec(entries);
                }
                // 商品图片
                CommodityImageModel thumbnail = commodityDao.getThumbnail(order.getCommodityId());
                if (thumbnail != null) {
                    order.setCommodityThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
            }
            return orders;
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
            String info = order.getInfo();

            if (!WsUtil.isEmpty(info)) {

                List<SpecEntryModel> entries = specDao.getEntriesBySpecInfo(WsUtil.getInfoLong(info));
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
     * 生成订单
     * @param userId userId
     * @param commodities commodities
     * @throws ServiceException
     */
    public void add(Long userId, String commodities) throws ServiceException {
        try {
            List<OrderModel> list = (List<OrderModel>) GsonUtil.json2list(commodities, new TypeToken<List<OrderModel>>() {}.getType());
            for (OrderModel model : list) {
                model.setUserId(userId);
                // 获取price, discount
                CommodityModel commodity = commodityDao.getById(model.getCommodityId());
                if(commodity == null) {
                    throw new ServiceException("商品未找到");
                }
                model.setCommodityId(commodity.getId());
                model.setPrice(commodity.getPrice());
                // TODO 获取discount
                Float discount = 0f;
                model.setDiscount(discount);

                // 获取info
                String info = model.getInfo();
                if (!WsUtil.isEmpty(info)) {
                    String[] infoArray = info.split(",");
                    List<CommoditySpecModel> specs = commodityDao.getSpec(commodity.getId());

                    if(infoArray.length != specs.size()) {
                        throw new ServiceException("规格参数数量不一致");
                    }

                    List<String> infoList = new ArrayList<String>();
                    for (String tmp : infoArray) {
                        boolean has = false;
                        for (CommoditySpecModel spec : specs) {
                            if(spec.getSpecEntryId().toString().equals(tmp)) {
                                has = true;
                                break;
                            }
                        }
                        if(has && !infoList.contains(tmp)) {
                            infoList.add(tmp);
                        }
                    }
                    if (infoList.size() != specs.size()) {
                        throw new ServiceException("规格参数错误");
                    }

                    // 验证数量 todo

                    model.setInfo(info);
                }

                // 获取address
                AddressModel address = addressDao.getById(model.getAddressId());
                if(address == null) {
                    throw new ServiceException("未找到寄送地址");
                }
                model.setAddress(address.getAddress());

                orderDao.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
