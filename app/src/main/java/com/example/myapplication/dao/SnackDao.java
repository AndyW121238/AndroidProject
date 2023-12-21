package com.example.myapplication.dao;

import com.example.myapplication.Bean.Snack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SnackDao {
    public static List<Snack> getSnack(){
        Connection connection = MyConnection.getConnection();
        List<Snack> snackList=new ArrayList<>();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "select name,type,price,img,detail from snack";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                String name = rs.getString(1);
                String type = rs.getString(2);
                double price = rs.getDouble(3);
                String image = rs.getString(4);
                String detail = rs.getString(5);
                Snack snack=new Snack(name,type,price,image,detail);
                snackList.add(snack);
            }
            rs.close();
            statement.close();
            connection.close();
            return snackList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static List<String> getSnackType(){
        Connection connection = MyConnection.getConnection();
        List<String> snackTypes=new ArrayList<>();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "select name from snacktype";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                String name = rs.getString(1);
                snackTypes.add(name);
            }
            rs.close();
            statement.close();
            connection.close();
            return snackTypes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
