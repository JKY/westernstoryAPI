package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class DictionaryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String comment;
    private String code;
    private Boolean isActive;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
