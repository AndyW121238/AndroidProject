package com.example.myapplication.activity;

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

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.adaptor.OrderAdapter;
import com.example.myapplication.dao.OrderDao;
import com.example.myapplication.Bean.Order;

import java.util.List;

//订单信息activity
public class OrderActivity extends AppCompatActivity {
    RecyclerView orderRecyclerView=null;
    List<Order> orders=null;
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
    //刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
    }

    //初始化订单页面
    private void setAdapter(){
        // 设置适配器
        OrderAdapter adapter = new OrderAdapter(orders);
        // 设置空布局
        adapter.setEmptyView(getEmptyView());
        orderRecyclerView.setAdapter(adapter);
    }
    private void initAdapter() {
        //从全局变量获取订单数据
        orders=MyApplication.getOrderList();
        if(orders==null){
            // 从服务器获取数据
            new Thread(()->{
                orders = OrderDao.selectOrder(MyApplication.getUser().getUsername());
                Message message=handler.obtainMessage();
                message.what=0x11;
                message.obj=orders;
                handler.sendMessage(message);
            }).start();
        }else {
            setAdapter();
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    orders= (List<Order>) msg.obj;
                    //添加到全局变量中
                    MyApplication.setOrderList(orders);
                    setAdapter();
                    break;
            }
        }
    };
    public View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.empty_order_view, orderRecyclerView, false);
    }
}