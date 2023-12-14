package com.study.food.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.study.food.model.User;
import com.study.food.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    // 实例化SharedPreferences对象
    private static final SharedPreferences data = Utils.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);

    // Gson对象
    private static final Gson gson = new Gson();

    public static boolean getLogin() {
        return data.getBoolean("getLogin", false);
    }

    public static void setLogin(boolean bool) {
        SharedPreferences.Editor edit = data.edit();
        edit.putBoolean("getLogin", bool);
        edit.apply();
    }
    //注册用户保存数据库
    public static void insertUser(User user){
        Connection connection= MyConnection.getConnection();
        if(connection==null){
            return;
        }
        String username=user.getUsername();
        String password=user.getPassword();
        try {
            String sql="insert into user(name,password) values(?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.execute();
            statement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static User selectUser(String username){
        Connection connection= MyConnection.getConnection();
        if(connection==null){
            return null;
        }
        try {
            String sql="select name,password from user where name=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            ResultSet rs=statement.executeQuery();
            if(rs==null){
                return null;
            }
            User user=null;
            while (rs.next()){
                String name=rs.getString(1);
                String password=rs.getString(2);
                user=new User(name,password);
            }
            rs.close();
            statement.close();
            connection.close();
            return user;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    //获取已登录用户对象
    public static User getUser() {
        String userJson = data.getString("user", "");
        return gson.fromJson(userJson, User.class);
    }
    public static void saveUser(User user) {
        String userJson = gson.toJson(user);
        SharedPreferences.Editor edit = data.edit();
        edit.putString("user", userJson);
        edit.apply();
    }
    //清除登录用户信息和登录状态
    public static void removeUserAndLoginStatus() {
        SharedPreferences.Editor edit = data.edit();
        edit.remove("user");
        edit.remove("getLogin");
        edit.apply();
    }

    //保存账号名字
    public static void saveUsername(String username) {
        SharedPreferences.Editor editor = data.edit();
        editor.putString("username", username);
        editor.apply();
    }

    //获取账号名字
    public static String getUsername() {
        return data.getString("username", "");
    }
}
