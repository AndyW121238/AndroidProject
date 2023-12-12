package com.study.food.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.food.MyApplication;
import com.study.food.R;
import com.study.food.adaptor.OrderAdapter;
import com.study.food.dao.OrderDao;
import com.study.food.dao.UserDao;
import com.study.food.model.Order;
import com.study.food.model.User;
import com.study.food.utils.Tips;

import java.util.List;


public class OrderActivity extends AppCompatActivity {
    RecyclerView orderRecyclerView=null;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("订单");
        orderRecyclerView=findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        initAdapter();
    }
    //初始化订单页面
    List<Order> orders;
    private void initAdapter() {
        // 获取数据库数据
        //List<Order> orders = OrderDao.findAllByUsername(MyApplication.getUser().getUsername());
        new Thread(()->{
            orders = OrderDao.selectOrder(MyApplication.getUser().getUsername());
            OrderAdapter adapter = new OrderAdapter(orders);
            // 设置空布局
            adapter.setEmptyView(getEmptyView());
            orderRecyclerView.setAdapter(adapter);
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    break;
                case 0x12:
                    break;
            }
        }
    };
    public View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.empty_order_view, orderRecyclerView, false);
    }
}