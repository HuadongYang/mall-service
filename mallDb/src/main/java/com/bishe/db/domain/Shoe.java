package com.bishe.db.domain;

import com.bishe.db.enums.ShoeEnum;


public class Shoe {
    private int id;
    private ShoeEnum brand;
    private int size;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShoeEnum getBrand() {
        return brand;
    }

    public void setBrand(ShoeEnum brand) {
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
