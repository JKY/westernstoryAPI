package com.westernstory.api.model;

import java.io.Serializable;
import java.util.List;

// Created by fedor on 15/5/13.
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String commodityName;
    private String commodityThumbnail;
    private String commoditySummary;
    private List<SpecEntryModel> selectedSpec;
    private List<CommodityImageModel> images;
    private Long addressId;

    private Long id;
    private Long userId;
    private Long commodityId;
    private Integer status;
    private String comment;
    private Long createTime;
    private Long updateTime;
    private String info;
    private Integer total;
    private String address;
    private String reason;
    private Boolean isActive;
    private Float price;
    private Float discount;
    private String number;
    private Boolean isPaid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public List<SpecEntryModel> getSelectedSpec() {
        return selectedSpec;
    }

    public void setSelectedSpec(List<SpecEntryModel> selectedSpec) {
        this.selectedSpec = selectedSpec;
    }

    public List<CommodityImageModel> getImages() {
        return images;
    }

    public void setImages(List<CommodityImageModel> images) {
        this.images = images;
    }

    public String getCommoditySummary() {
        return commoditySummary;
    }

    public void setCommoditySummary(String commoditySummary) {
        this.commoditySummary = commoditySummary;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setCommodityThumbnail(String commodityThumbnail) {
        this.commodityThumbnail = commodityThumbnail;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
