package com.westernstory.api.dao;

import com.westernstory.api.model.DictionaryEntryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface DictionaryDao {
    /**
     * 根据code获取 List<DictionaryEntry>
     * @param code code
     * @return List<DictionaryEntry>
     * @throws Exception
     */
    List<DictionaryEntryModel> listDictionariesByCode(@Param("code")String code) throws Exception;
}
