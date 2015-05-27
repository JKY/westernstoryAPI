package com.westernstory.api.service;

import com.google.common.reflect.TypeToken;
import com.westernstory.api.config.Config;
import com.westernstory.api.dao.AddressDao;
import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.dao.DictionaryDao;
import com.westernstory.api.dao.OrderDao;
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
    private DictionaryDao dictionarydao = null;
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
                String info = order.getInfo();
                if(!WsUtil.isEmpty(info)) {
                    String[] codes = info.split("\\|");
                    List<DictionaryEntryModel> entries = dictionarydao.getByEntryCodes(codes);
                    order.setSelectedSkus(entries);
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
            List<DictionaryEntryModel> entries = new ArrayList<DictionaryEntryModel>();
            String info = order.getInfo();

            if (!WsUtil.isEmpty(info)) {
                String[] codes = info.split("\\|");
                // 获取选中的sku
                List<DictionaryEntryModel> selectedEntries = dictionarydao.getByEntryCodes(codes);
                String tmp = "";
                for (DictionaryEntryModel entry : selectedEntries) {
                    tmp += entry.getDictCode() + "|";
                }
                if (!WsUtil.isEmpty(tmp)) {
                    tmp = tmp.substring(0, tmp.length()-1);
                    // 获取所有sku
                    entries = dictionarydao.getByDictCodes(tmp.split("\\|"));
                    for (DictionaryEntryModel entry : entries) {
                        entry.setIsSelected(false);
                        for (DictionaryEntryModel selected : selectedEntries) {
                            if (entry.getEntryCode().equals(selected.getEntryCode())) {
                                entry.setIsSelected(true);
                                break;
                            }
                        }
                    }
                }
            }

            // 组织sku
            List<DictionaryModel> skus = new ArrayList<DictionaryModel>();
            for (DictionaryEntryModel entry : entries) {
                DictionaryModel tmp = null;
                for (DictionaryModel sku : skus) {
                    if (sku.getCode().equals(entry.getDictCode())) {
                        tmp = sku;
                        break;
                    }
                }

                if(tmp == null) {
                    DictionaryModel dict = new DictionaryModel();
                    dict.setName(entry.getDictName());
                    dict.setCode(entry.getDictCode());
                    dict.setId(entry.getDictionaryId());

                    List<DictionaryEntryModel> dictEntries = new ArrayList<DictionaryEntryModel>();
                    entry.setDictName(null);
                    entry.setDictCode(null);
                    entry.setDictionaryId(null);
                    dictEntries.add(entry);

                    dict.setEntries(dictEntries);
                    skus.add(dict);
                } else {
                    entry.setDictName(null);
                    entry.setDictCode(null);
                    entry.setDictionaryId(null);
                    tmp.getEntries().add(entry);
                }
            }

            order.setSkus(skus);

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

                // 获取info name
                String[] infos = dictionarydao.getInfoName(model.getInfo().split("\\|"));
                String infoString = "";
                for (String info : infos) {
                    infoString += info + "|";
                }
                if(!WsUtil.isEmpty(infoString)) {
                    infoString = infoString.substring(0, infoString.length()-1);
                }
                if (infos.length != model.getInfo().split("\\|").length) {
                    throw new ServiceException("规格配置错误");
                }
                model.setInfo(infoString);

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
