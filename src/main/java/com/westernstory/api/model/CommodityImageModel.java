package com.westernstory.api.model;

import java.io.Serializable;
import java.util.List;

// Created by fedor on 15/5/13.
public class CommodityImageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long commodityId;
    private String image;
    private Boolean isActive;
    private Long createBy;
    private Long createTime;
    private Long updateBy;
    private Long updateTime;

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

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUpdateTime() {
        return updateTime;

    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
