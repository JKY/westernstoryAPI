package com.westernstory.api.model;

import java.io.Serializable;

// Created by fedor on 15/5/13.
public class CommodityCategoryClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private Boolean isHeadline;
    private String icon;
    private String code;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getIsHeadline() {
        return isHeadline;
    }

    public void setIsHeadline(Boolean isHeadline) {
        this.isHeadline = isHeadline;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
