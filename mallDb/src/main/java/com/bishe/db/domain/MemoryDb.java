package com.bishe.db.domain;


import java.util.Date;


public class MemoryDb {

    private String key;

    private String value;

    private Date failureTime;

    public MemoryDb() {
    }

    public MemoryDb(String key, String value, Date failureTime) {
        this.key = key;
        this.value = value;
        this.failureTime = failureTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }
}
