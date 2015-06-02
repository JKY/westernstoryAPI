package com.westernstory.api.dao;

import com.westernstory.api.model.CommoditySpecModel;
import com.westernstory.api.model.SpecEntryModel;
import com.westernstory.api.model.SpecModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface SpecDao {
    /**
     * 获取规格列表
     * @return List<SpecModel>
     * @throws Exception
     */
    List<SpecModel> get(@Param("start") Integer start, @Param("limit") Integer limit,
                        @Param("ad") SpecModel ad, @Param("orderby") List<String> orderby) throws Exception;

    /**
     * 添加规格
     * @param spec spec
     * @throws Exception
     */
    void add(SpecModel spec) throws Exception;

    /**
     * 修改规格
     * @param spec spec
     * @throws Exception
     */
    void update(SpecModel spec) throws Exception;

    /**
     * 删除规格
     * @param ids ids
     * @param updateBy updateBy
     * @throws Exception
     */
    void remove(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy) throws Exception;

    /**
     * 根据条件查询总记录数量
     * @param where where
     * @return Long
     * @throws Exception
     */
    Long count(@Param("model") SpecModel where) throws Exception;

    /**
     * 通过specId删除对应的所有entry
     * @param specId specId
     * @param updateBy updateBy
     * @throws Exception
     */
    void removeAllEntries(@Param("specId") Long specId, @Param("updateBy") Long updateBy) throws Exception;

    /**
     * 通过id删除数据字典
     * @param id id
     * @param updateBy updateBy
     * @throws Exception
     */
    void removeById(@Param("id") Long id, @Param("updateBy") Long updateBy) throws Exception;

    /**
     * 相关规格，所包含产品
     * @param specId specId
     * @return CommodityModel
     * @throws Exception
     */
    CommoditySpecModel testRemoveSpec(@Param("specId") Long specId) throws Exception;

    /**
     * 相关规格，所包含产品
     * @param entryId entryId
     * @return CommodityModel
     * @throws Exception
     */
    CommoditySpecModel testRemoveSpecEntry(@Param("entryId") Long entryId) throws Exception;

    /**
     * 通过spec id 获取entries
     * @param specId specId
     * @return List<SpecEntryModel>
     * @throws Exception
     */
    List<SpecEntryModel> getEntries(@Param("specId") Long specId) throws Exception;

    /**
     * 通过name获取Spec
     * @param name name
     * @return SpecModel
     * @throws Exception
     */
    SpecModel getByName(@Param("name") String name) throws Exception;

    /**
     * 通过name获取Spec 除了某id
     * @param name name
     * @param id id
     * @return SpecModel
     * @throws Exception
     */
    SpecModel getByNameExceptId(@Param("name") String name, @Param("id") Long id) throws Exception;

    /**
     * 通过entry name 获取entry
     * @param name
     * @return SpecEntryModel
     * @throws Exception
     */
    SpecEntryModel getEntryByEntryName(@Param("name") String name) throws Exception;

    /**
     * 通过entry name 获取entry 除了某id
     * @param name name
     * @param id id
     * @return SpecEntryModel
     * @throws Exception
     */
    SpecEntryModel getEntryByEntryNameExceptId(@Param("name") String name, @Param("id") Long id) throws Exception;

    /**
     * 添加entry
     * @param entry entry
     * @return Long
     * @throws Exception
     */
    Long addEntry(SpecEntryModel entry) throws Exception;

    /**
     * 修改entry
     * @param entry entry
     * @throws Exception
     */
    void updateEntry(SpecEntryModel entry) throws Exception;

    /**
     * 删除entry
     * @param ids ids
     * @param updateBy updateBy
     * @throws Exception
     */
    void removeEntries(@Param("ids") List<Long> ids, @Param("updateBy") Long updateBy) throws Exception;

    /**
     * 模糊查询规格
     * @param name name
     * @return list
     * @throws Exception
     */
    List<SpecModel> query(@Param("name") String name) throws Exception;

    /**
     * 模糊查询规格实体
     * @param name name
     * @param specId specId
     * @return list
     * @throws Exception
     */
    List<SpecEntryModel> queryEntry(@Param("name") String name, @Param("specId") Long specId) throws Exception;

    /**
     * 模糊查询规格, 排除某些id
     * @param name name
     * @param ids ids
     * @return List<SpecModel>
     * @throws Exception
     */
    List<SpecModel> queryExcept(@Param("name") String name, @Param("ids") List<Long> ids) throws Exception;

    /**
     * 通过spec info 获取规格
     * @param info info
     * @return List<SpecEntryModel>
     * @throws Exception
     */
    List<SpecEntryModel> getEntriesBySpecInfo(@Param("info") List<Long> info) throws Exception;
}
