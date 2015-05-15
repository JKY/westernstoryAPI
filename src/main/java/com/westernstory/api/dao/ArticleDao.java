package com.westernstory.api.dao;

import com.westernstory.api.model.ArticleModel;
import com.westernstory.api.model.ArticleTagModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// Created by fedor on 15/5/13.
public interface ArticleDao {
    /**
     * 根据categoryId获取文章列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List<ArticleModel>
     * @throws Exception
     */
    List<ArticleModel> list(@Param("categoryId")Long categoryId, @Param("start")Integer start, @Param("limit")Integer limit) throws Exception;

    /**
     * 通过articleIds 获取文章tags
     * @param articleIds articleIds
     * @return List<ArticleTagModel>
     * @throws Exception
     */
    List<ArticleTagModel> getTagsByActicleIds(@Param("articleIds")List<Long> articleIds) throws Exception;
}