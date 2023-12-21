package com.example.myapplication.Bean;

import java.io.Serializable;

//食品类
public class Snack implements Serializable {
    //名称
    private String name;
    private String type;
    //单价
    private double price;
    //图片资源
    private String image;
    //简介详情
    private String detail;
    //数量
    private int count;
    public Snack() {
    }
    public Snack(String name, String type, double price, String image, String detail) {
        this.name = name;
        this.type=type;
        this.price = price;
        this.image = image;
        this.detail = detail;
        this.count = 1;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
