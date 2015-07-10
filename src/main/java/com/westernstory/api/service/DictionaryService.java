package com.westernstory.api.service;

import com.westernstory.api.dao.DictionaryDao;
import com.westernstory.api.model.DictionaryEntryModel;
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
public class DictionaryService {
    @Autowired
    private DictionaryDao dictionaryDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 根据code获取 List<DictionaryEntry>
     * @param code code
     * @return List
     * @throws com.westernstory.api.util.ServiceException
     */
    public List<DictionaryEntryModel> listDictionariesByCode(String code) throws ServiceException {
        try {
            return dictionaryDao.listDictionariesByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
