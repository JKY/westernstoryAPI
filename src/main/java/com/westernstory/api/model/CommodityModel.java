package com.westernstory.api.model;

import java.io.Serializable;
import java.util.List;

// Created by fedor on 15/5/13.
public class CommodityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private List<DictionaryModel> skus;

    private Long id;
    private Long categoryId;
    private String name;
    private Float priceOriginal;
    private Float pirceDiscount;
    private Boolean isActive;
    private Long createBy;
    private Long createTime;
    private Long updateBy;
    private Long updateTime;
    private String thumbnail;
    private String summary;
    private String content;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(Float priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public Float getPirceDiscount() {
        return pirceDiscount;
    }

    public void setPirceDiscount(Float pirceDiscount) {
        this.pirceDiscount = pirceDiscount;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<DictionaryModel> getSkus() {
        return skus;
    }

    public void setSkus(List<DictionaryModel> skus) {
        this.skus = skus;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
