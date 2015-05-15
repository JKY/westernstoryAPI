package com.westernstory.api.service;

import com.westernstory.api.dao.ArticleDao;
import com.westernstory.api.model.ArticleModel;
import com.westernstory.api.model.ArticleTagModel;
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
public class ArticleService {
    @Autowired
    private ArticleDao artitcleDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 根据categoryId获取文章列表
     * @param categoryId categoryId
     * @param start start
     * @param limit limit
     * @return List<ArticleModel>
     */
    public List<ArticleModel> list(Long categoryId, Integer start, Integer limit) throws ServiceException {
        try {
            List<ArticleModel> articles = artitcleDao.list(categoryId, start, limit);

            List<Long> articleIds = new ArrayList<Long>();
            for(ArticleModel articleModel: articles) {
                articleIds.add(articleModel.getId());
            }
            if (articleIds.size() > 0) {
                // 获取文章tags
                List<ArticleTagModel> tags = artitcleDao.getTagsByActicleIds(articleIds);
                for(ArticleModel article: articles) {
                    List<ArticleTagModel> articleTags=new ArrayList<ArticleTagModel>();
                    for(ArticleTagModel tag:tags){
                        if(article.getId().equals(tag.getArticleId())){
                            articleTags.add(tag);
                        }
                    }
                    article.setTags(articleTags);
                }
            }

            return articles;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
