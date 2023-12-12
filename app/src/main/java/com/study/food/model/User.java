package com.study.food.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;

    private String username;

    private String password;

    private int headImage;

    public User() {
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int headImage) {
        this.username = username;
        this.password = password;
        this.headImage = headImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }
}
