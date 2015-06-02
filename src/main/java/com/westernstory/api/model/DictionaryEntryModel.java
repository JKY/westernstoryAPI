package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class DictionaryEntryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    private String dictName;
    private String entryName;
    private String entryCode;
    private String dictCode;
    private Boolean isSelected;
    private String lastModifyUser;
    private String iconURL;
    private Long count;
    private Boolean isHeadline;

    private long id;
    private String info;
    private Long dictionaryId;
    private String name;
    private Boolean isActive;
    private String code;
    private Long createBy;
    private Long updateBy;
    private Long createTime;
    private Long updateTime;
    private String icon;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public String getName() {
        return name;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setIsActive(Boolean isActive) {

        this.isActive = isActive;

    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getIsHeadline() {
        return isHeadline;
    }

    public void setIsHeadline(Boolean isHeadline) {
        this.isHeadline = isHeadline;
    }
}
