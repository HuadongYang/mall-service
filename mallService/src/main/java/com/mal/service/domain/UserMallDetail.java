package com.mal.service.domain;

import java.math.BigInteger;

public class UserMallDetail {
    private Integer id;
    private Integer userId;
    private Integer mallId;
    private Integer preferenceValue;
    private BigInteger timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMallId() {
        return mallId;
    }

    public void setMallId(Integer mallId) {
        this.mallId = mallId;
    }

    public Integer getPreferenceValue() {
        return preferenceValue;
    }

    public void setPreferenceValue(Integer preferenceValue) {
        this.preferenceValue = preferenceValue;
    }
}
