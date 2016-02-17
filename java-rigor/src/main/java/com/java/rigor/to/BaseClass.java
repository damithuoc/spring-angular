package com.java.rigor.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by sanandasena on 1/12/2016.
 */
public abstract class BaseClass implements Serializable {

    private static final long serialVersionUID = 5276351233749715139L;
    private String createdBy;
    private Long createdDate;
    private String updatedBy;
    private Long updatedDate;
    private Boolean isActive;

    @JsonIgnore(value = true)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonIgnore(value = true)
    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @JsonIgnore(value = true)
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonIgnore(value = true)
    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
