package com.westernstory.api.dao;

import com.westernstory.api.model.CommodityImageModel;
import com.westernstory.api.model.CommodityModel;
import com.westernstory.api.model.CommoditySpecModel;
import com.westernstory.api.model.DictionaryEntryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface CommodityDao {
    /**
     * 根据类别获取商品列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> get(@Param("categoryId")Integer categoryId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 关键字查询商品
     * @param keyword keyword
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> getByKeyword(@Param("keyword")String keyword, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 探索最新商品
     * @param start start
     * @param limit limit
     * @return List<CommodityModel>
     * @throws Exception
     */
    List<CommodityModel> getLatest(@Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 通过id获取商品详情
     * @param id id
     * @return CommodityModel
     * @throws Exception
     */
    CommodityModel getById(@Param("id")Long id) throws Exception;

    /**
     * 获取推荐类别
     * @return DictionaryEntryModel
     * @throws Exception
     */
    DictionaryEntryModel getHeadline() throws Exception;

    /**
     * 根据商品id获取商品缩略图
     * @param commodityId commodityId
     * @return CommodityImageModel
     * @throws Exception
     */
    CommodityImageModel getThumbnail(@Param("commodityId")Long commodityId) throws Exception;

    /**
     *  根据商品id获取商品图片
     * @param commodityId commodityId
     * @return List<CommodityImageModel>
     * @throws Exception
     */
    List<CommodityImageModel> getImages(@Param("commodityId")Long commodityId) throws Exception;
    /**
     * 通过商品id获取商品规格
     * @param cid cid
     * @return List<CommoditySpecModel>
     * @throws Exception
     */
    List<CommoditySpecModel> getSpec(Long cid) throws Exception;

    /**
     * 通过商品id获取购买人的个数
     * @param commodityId commodityId
     * @return count
     * @throws Exception
     */
    Integer getBuyCountFromNoneSpec(Long commodityId) throws Exception;

    /**
     * 根据分类获取总数
     * @param categoryId categoryId
     * @return long
     * @throws Exception
     */
    Long count(@Param("categoryId")Integer categoryId) throws Exception;

    /**
     * 根据关键查询商品总数
     * @param keyword keyword
     * @return long
     * @throws Exception
     */
    Long countByKeyword(@Param("keyword")String keyword) throws Exception;

    /**
     * 获取最新商品总数
     * @return long
     * @throws Exception
     */
    Long countLatest() throws Exception;
}
