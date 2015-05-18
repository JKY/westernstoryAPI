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

    private long id;
    private String comment;
    private Long dictionaryId;
    private String name;
    private Boolean isActive;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
