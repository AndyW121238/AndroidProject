package com.example.myapplication.adaptor;

import android.annotation.SuppressLint;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.activity.EvaluateActivity;
import com.example.myapplication.activity.OrderDetailActivity;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.Bean.Order;

import com.example.myapplication.utils.Tips;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder>{

    public OrderAdapter(List<Order> orders) {
        super(R.layout.item_order, orders);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Order order) {
        baseViewHolder.setText(R.id.orderName, order.getName());
        ImageView image=baseViewHolder.findView(R.id.orderImage);
        ImageUtils.setImageBitmap(image,order.getImage(),getContext());
        //点击显示订单细节
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.orderImage){
                    //启动订单细节页面
                    OrderDetailActivity.actionStart(getContext(), order);
                }
            }
        });
        baseViewHolder.setText(R.id.orderTime, "下单时间: " + order.getTime());
        baseViewHolder.setText(R.id.orderCount,"数量："+order.getCount());
        baseViewHolder.setText(R.id.orderMoney, "总价: ￥" + order.getMoney());
        if(order.getEvaluate()!=null){
            baseViewHolder.setText(R.id.evaluateButton,"已评价");
        }
        //评价按钮监听器
        baseViewHolder.findView(R.id.evaluateButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.evaluateButton){
                    if(order.getEvaluate()!=null){
                        Tips.show("已经评价过了");
                    }else {
                        //启动评价页面
                        EvaluateActivity.actionStart(getContext(), order);
                    }
                }
            }
        });
    }
}
