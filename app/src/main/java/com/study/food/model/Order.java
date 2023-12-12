package com.study.food.model;

import android.annotation.SuppressLint;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    private String name;

    private int image;
    private int count;

    private double money;

    private String time;

    private String username;

    public Order(){}

    public Order(String name, int image, int count,double money, String time,String username) {
        this.name = name;
        this.image = image;
        this.count=count;
        this.money = money;
        this.time = time;
        this.username=username;
    }

    //导入订单,计算金额、时间
    @SuppressLint("SimpleDateFormat")
    public Order(Snack snack,String username) {
        this.name = snack.getName();
        this.image = snack.getImage();
        this.count=snack.getCount();
        // 计算金额
        BigDecimal money = BigDecimal.valueOf(snack.getPrice()).multiply(BigDecimal.valueOf(snack.getCount()));
        this.money = money.doubleValue();
        // 订单产生时间（格式化）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = simpleDateFormat.format(new Date());
        this.username=username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
