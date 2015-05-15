package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class CommonPhoneEntryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long commonPhoneId;
    private String name;
    private String phone;
    private Boolean isActive;
    private Long createTime;
    private Long createBy;
    private Long updateBy;
    private Long updateTime;
    private Integer clickCount;

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

    public Long getCommonPhoneId() {
        return commonPhoneId;
    }

    public void setCommonPhoneId(Long commonPhoneId) {
        this.commonPhoneId = commonPhoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
