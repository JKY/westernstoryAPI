package com.westernstory.api.service;

import com.westernstory.api.config.Config;
import com.westernstory.api.dao.CommodityDao;
import com.westernstory.api.model.*;
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

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 根据类别获取商品列表
     * @param categoryId categoryId
     * @param start start
     * @return List<CommodityModel>
     * @throws ServiceException
     */
    public List<CommodityModel> get(Integer categoryId, Integer start, Integer limit) throws ServiceException {
        try {
            List<CommodityModel> list = commodityDao.get(categoryId, start, limit);
            for (CommodityModel model : list) {
                CommodityImageModel thumbnail = commodityDao.getThumbnail(model.getId());
                if (thumbnail != null) {
                    model.setThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
                // TODO 商品折扣价格
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
     * 关键字查询商品
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     */
    public List<CommodityModel> getByKeyword(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            List<CommodityModel> list = commodityDao.getByKeyword(keyword, start, limit);
            for (CommodityModel model : list) {
                CommodityImageModel thumbnail = commodityDao.getThumbnail(model.getId());
                if (thumbnail != null) {
                    model.setThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
            }
            return list;
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
            List<CommodityModel> list = commodityDao.getLatest(start, limit);
            for (CommodityModel model : list) {
                CommodityImageModel thumbnail = commodityDao.getThumbnail(model.getId());
                if (thumbnail != null) {
                    model.setThumbnail(Config.URL_STATIC + thumbnail.getImage());
                }
            }
            return list;
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

            // Spec
            List<CommoditySpecModel> css = commodityDao.getSpec(model.getId());
            if (css.size() > 0) {
                List<SpecModel> specResult = new ArrayList<SpecModel>();
                for (CommoditySpecModel cs : css) {
                    SpecModel tmp = null;
                    for (SpecModel s : specResult) {
                        if (cs.getSpecId().equals(s.getId())) {
                            tmp = s;
                            break;
                        }
                    }
                    if(tmp != null) {
                        SpecEntryModel entry = new SpecEntryModel();
                        entry.setId(cs.getSpecEntryId());
                        entry.setName(cs.getSpecEntryName());
                        tmp.getEntries().add(entry);
                    } else {
                        SpecEntryModel entry = new SpecEntryModel();
                        entry.setId(cs.getSpecEntryId());
                        entry.setName(cs.getSpecEntryName());

                        List<SpecEntryModel> entries = new ArrayList<SpecEntryModel>();
                        entries.add(entry);

                        SpecModel spec = new SpecModel();
                        spec.setId(cs.getSpecId());
                        spec.setName(cs.getSpecName());
                        spec.setEntries(entries);

                        specResult.add(spec);
                    }
                }
                model.setSpecs(specResult);
            }

            // 商品图片
            List<CommodityImageModel> images = commodityDao.getImages(model.getId());
            for (CommodityImageModel image : images) {
                image.setImage(Config.URL_STATIC + image.getImage());
            }
            model.setImages(images);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取推荐类别
     * @return CommodityCategoryClass
     * @throws ServiceException
     */
    public DictionaryEntryModel getHeadline() throws ServiceException {
        try {
            DictionaryEntryModel model = commodityDao.getHeadline();
            if (model != null) {
                model.setIcon(Config.URL_STATIC + model.getIcon());
                return model;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
