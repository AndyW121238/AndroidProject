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

    private static final DatabaseHelper dbHelper = new DatabaseHelper(Utils.getContext(), 1);

    static {
        dbHelper.getWritableDatabase();
    }
    //保存订单数据
    public static void saveOrder(List<Order> orders) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (Order order : orders) {
            ContentValues values = new ContentValues();
            values.put("name", order.getName());
            values.put("image", order.getImage());
            values.put("count",order.getCount());
            values.put("money", order.getMoney());
            values.put("time", order.getTime());
            values.put("username", order.getUsername());
            db.insert("orders", null, values);
        }
        db.close();
    }
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
    public static List<Order> findAllByUsername(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Order> orders = new ArrayList<>();
        // 查询指定用户名订单
        Cursor cursor = db.query("orders", null, "username=?", new String[]{username}, null, null, "time desc");
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int image = cursor.getInt(cursor.getColumnIndex("image"));
                int count=cursor.getInt(cursor.getColumnIndex("count"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                Order order = new Order(name, image,count, money, time,username);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }
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
