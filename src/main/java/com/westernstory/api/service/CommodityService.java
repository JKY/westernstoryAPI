package com.westernstory.api.service;

import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.dao.DictionaryDao;
import com.westernstory.api.model.CommodityModel;
import com.westernstory.api.model.DictionaryEntryModel;
import com.westernstory.api.model.DictionaryModel;
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
public class CommodityService {
    @Autowired
    private CommodityDao commodityDao = null;
    @Autowired
    private DictionaryDao dictionarydao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 根据类别获取商品列表
     * @param categoryId categoryId
     * @param start start
     * @param start start
     * @return List<CommodityModel>
     * @throws ServiceException
     */
    public List<CommodityModel> get(Integer categoryId, Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.get(categoryId, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 关键字查询商品
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     */
    public List<CommodityModel> getByKeyword(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.getByKeyword(keyword, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取最新商品
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     */
    public  List<CommodityModel> getLatest(Integer start, Integer limit) throws ServiceException {
        try {
            return commodityDao.getLatest(start, limit);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 通过id获取商品详情
     * @param id id
     * @return CommodityModel
     * @throws ServiceException
     */
    public CommodityModel getDetail(Long id) throws ServiceException {
        try {
            CommodityModel model = commodityDao.getById(id);
            String info = model.getInfo();
            // sku
            if(!WsUtil.isEmpty(info)) {
                String[] codes = info.split("\\|");
                List<DictionaryEntryModel> entries = dictionarydao.getByDictCodes(codes);
                List<DictionaryModel> dicts = new ArrayList<DictionaryModel>();

                for (DictionaryEntryModel entry : entries) {
                    Boolean has = false;
                    for(DictionaryModel dict : dicts) {
                        // entries add
                        if (entry.getDictionaryId().equals(dict.getId())) {
                            entry.setDictName(null);
                            entry.setDictCode(null);
                            entry.setDictionaryId(null);
                            dict.getEntries().add(entry);
                            has = true;
                            break;
                        }
                    }
                    if(!has) {
                        // set entries
                        DictionaryModel dict = new DictionaryModel();
                        dict.setCode(entry.getDictCode());
                        dict.setName(entry.getDictName());
                        dict.setId(entry.getDictionaryId());

                        List<DictionaryEntryModel> tmp = new ArrayList<DictionaryEntryModel>();
                        entry.setDictName(null);
                        entry.setDictCode(null);
                        entry.setDictionaryId(null);
                        tmp.add(entry);
                        dict.setEntries(tmp);

                        dicts.add(dict);
                    }
                }
                model.setSkus(dicts);
            }
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
