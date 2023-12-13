package com.study.food.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.study.food.model.Order;
import com.study.food.model.User;
import com.study.food.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    //保存订单数据
    public static void insertOrder(List<Order> orders){
        Connection connection=MyConnection.getConnection();
        if (connection == null) {
            return;
        }
        try {
            for (Order order : orders){
                String snackName=order.getName();
                String image= String.valueOf(order.getImage());
                String count= String.valueOf(order.getCount());
                String money= String.valueOf(order.getMoney());
                String time=order.getTime();
                String username=order.getUsername();
                String sql = "insert into orders(snack_name,image,count,money,time,username) values(?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,snackName);
                statement.setString(2,image);
                statement.setString(3,count);
                statement.setString(4,money);
                statement.setString(5,time);
                statement.setString(6,username);
                statement.execute();
                statement.close();
            }
            connection.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //通过用户名查询订单数据
    public static List<Order> selectOrder(String username){
        Connection connection = MyConnection.getConnection();
        List<Order> orders=new ArrayList<>();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "select snack_name,image,count,money,time from orders where username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                String name = rs.getString(1);
                int image = rs.getInt(2);
                int count = rs.getInt(3);
                double money = rs.getDouble(4);
                String time= rs.getString(5);
                Order order = new Order(name,image,count,money,time,username);
                orders.add(order);
            }
            rs.close();
            statement.close();
            connection.close();
            return orders;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
