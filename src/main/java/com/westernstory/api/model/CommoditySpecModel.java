package com.westernstory.api.model;

/**
 * Created by fedor on 15/5/30.
 */
public class CommoditySpecModel {
    private static final long serialVersionUID = 1L;

    //
    private String specEntryName;
    private String specName;
    private Long specId;

    private Long id;
    private Long commodityId;
    private Long specEntryId;
    private Long createTime;
    private Long createBy;

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

    public Long getSpecEntryId() {
        return specEntryId;
    }

    public void setSpecEntryId(Long specEntryId) {
        this.specEntryId = specEntryId;
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

    public String getSpecEntryName() {
        return specEntryName;
    }

    public void setSpecEntryName(String specEntryName) {
        this.specEntryName = specEntryName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }
}
