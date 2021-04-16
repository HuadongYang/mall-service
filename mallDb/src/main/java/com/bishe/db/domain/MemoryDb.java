package com.bishe.db.domain;


import java.util.Date;
import java.util.Objects;


public class MemoryDb {

    private String key;

    private String value;

    private Date failureTime;

    private Integer count;

    public MemoryDb() {
    }

    public MemoryDb(String key, String value, Date failureTime) {
        this.key = key;
        this.value = value;
        this.failureTime = failureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryDb memoryDb = (MemoryDb) o;
        return getKey().equals(memoryDb.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
