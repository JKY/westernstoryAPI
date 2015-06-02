package com.westernstory.api.model;

import java.io.Serializable;
import java.util.List;

// Created by fedor on 15/5/13.
public class SpecModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String lastModifyUser;
    private List<SpecEntryModel> entries;


    private Long id;
    private String name;
    private String comment;
    private Boolean isActive;
    private Long createTime;
    private Long updateTime;
    private Long createBy;
    private Long updateBy;

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
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

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public List<SpecEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(List<SpecEntryModel> entries) {
        this.entries = entries;
    }
}
