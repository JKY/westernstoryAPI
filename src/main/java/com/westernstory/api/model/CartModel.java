package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class CartModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String commodityName;
    private String commodityThumbnail;

    private Long id;
    private Long commodityId;
    private Boolean isActive;
    private Long userId;
    private Integer total;
    private String info;
    private String comment;
    private Long createTime;
    private Long updateTime;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityThumbnail() {
        return commodityThumbnail;
    }

    public void setCommodityThumbnail(String commodityThumbnail) {
        this.commodityThumbnail = commodityThumbnail;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
