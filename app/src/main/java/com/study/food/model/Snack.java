package com.study.food.model;

import java.io.Serializable;

//食品类
public class Snack implements Serializable {
    //名称
    private String name;
    //单价
    private double price;
    //图片资源
    private int image;
    //简介详情
    private String detail;
    //数量
    private int count;
    public Snack() {
    }
    public Snack(String name, double price, int image, String detail) {
        this.name = name;
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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
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
