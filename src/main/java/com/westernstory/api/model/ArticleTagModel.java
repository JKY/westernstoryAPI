package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class ArticleTagModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private Long articleId;

    private Long id;
    private String name;
    private Boolean isActive;
    private Long createBy;
    private Long createTime;
    private Long updateBy;
    private Long updateTime;
    private Long publishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }
}
