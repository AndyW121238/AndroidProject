package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;

import android.os.Handler;
import android.os.Message;
import com.example.myapplication.Bean.Order;
import com.example.myapplication.dao.SnackDao;
import com.example.myapplication.Bean.Snack;
import com.example.myapplication.Bean.User;
import com.example.myapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    //购物车的小吃
    private static List<Snack> cartSnacks;
    //小吃list
    private static List<Snack> snackList;
    //小吃种类list
    private static List<String> snackTypes;
    //订单list
    private static List<Order> orderList;
    //登录用户
    private static User user;
    //登录状态
    private static boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化购物车集合
        cartSnacks = new ArrayList<>();
        // 初始化工具类
        Utils.init(this);
        //获取服务器信息
        getSnack();
        getSnackType();
    }
    //添加新的订单
    public static void addOrderList(List<Order> orders){
        orderList.addAll(orders);
    }
    //设置评价信息
    public static void setOrderEvaluate(String id,String evaluate){
        int i;
        for (i=0;i<orderList.size();i++){
            if(orderList.get(i).getId().equals(id)){
                Order order=orderList.get(i);
                order.setEvaluate(evaluate);
                orderList.set(i,order);
                break;
            }
        }
    }
    //获取订单
    public void getSnack(){
        new Thread(()->{
            Message message=handler.obtainMessage();
            List<Snack> snackList = SnackDao.getSnack();

            if(snackList!=null){
                message.what=0x11;
                message.obj=snackList;
            }else {
                message.what=0x13;
            }
            handler.sendMessage(message);
        }).start();
    }
    //获取订单种类
    public void getSnackType(){
        new Thread(()->{
            Message message=handler.obtainMessage();
            List<String> snackTypes = SnackDao.getSnackType();
            if(snackTypes!=null){
                message.what=0x12;
                message.obj=snackTypes;
            }else {
                message.what=0x13;
            }
            handler.sendMessage(message);
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    List<Snack> snackList= (List<Snack>) msg.obj;
                    setSnackList(snackList);
                    break;
                case 0x12:
                    List<String> snackTypes= (List<String>) msg.obj;
                    setSnackTypes(snackTypes);
                    break;
                case 0x13:
                    System.out.println("网络错误");
                    break;
            }
        }
    };
    public static List<Snack> getCartSnacks() {
        return cartSnacks;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    //判断是否登录
    public static boolean getLogin() {
        return isLogin;
    }

    public static void setLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    public static void setSnackList(List<Snack> snackList) {
        MyApplication.snackList = snackList;
    }

    public static List<Snack> getSnackList() {
        return snackList;
    }

    public static void setSnackTypes(List<String> snackTypes) {
        MyApplication.snackTypes = snackTypes;
    }

    public static List<String> getSnackTypes() {
        return snackTypes;
    }
    public static void setOrderList(List<Order> orderList) {
        MyApplication.orderList = orderList;
    }
    public static List<Order> getOrderList() {
        return orderList;
    }
}
