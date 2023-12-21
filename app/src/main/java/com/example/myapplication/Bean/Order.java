package com.example.myapplication.Bean;

import android.annotation.SuppressLint;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    //订单id
    private String id;
    //小吃名称
    private String name;
    //图片路径
    private String image;
    //食品数量
    private int count;
    //总价钱
    private double money;
    //时间
    private String time;
    //用户名
    private String username;
    //评价
    private String evaluate;

    public Order(){}

    public Order(String id,String name, String image, int count,double money, String time,String username,String evaluate) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.count=count;
        this.money = money;
        this.time = time;
        this.username=username;
        this.evaluate=evaluate;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
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
    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}
