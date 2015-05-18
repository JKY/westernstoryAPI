package com.westernstory.api.service;

import com.westernstory.api.dao.DictionaryDao;
import com.westernstory.api.dao.OrderDao;
import com.westernstory.api.model.DictionaryEntryModel;
import com.westernstory.api.model.DictionaryModel;
import com.westernstory.api.model.OrderModel;
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
                List<DictionaryEntryModel> selectedEntries = dictionarydao.getByEntryCodes(codes);
                String tmp = "";
                for (DictionaryEntryModel entry : selectedEntries) {
                    tmp += entry.getDictCode() + "|";
                }
                if (!WsUtil.isEmpty(tmp)) {
                    tmp = tmp.substring(0, tmp.length()-1);
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

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
