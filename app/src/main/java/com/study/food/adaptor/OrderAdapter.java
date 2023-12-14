package com.study.food.adaptor;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.study.food.R;
import com.study.food.model.Order;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public OrderAdapter(List<Order> orders) {
        super(R.layout.item_order, orders);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Order order) {
        baseViewHolder.setText(R.id.orderName, order.getName());
        baseViewHolder.setImageResource(R.id.orderImage, order.getImage());
        baseViewHolder.setText(R.id.orderTime, "下单时间: " + order.getTime());
        baseViewHolder.setText(R.id.orderCount,"数量："+order.getCount());
        baseViewHolder.setText(R.id.orderMoney, "总价: ￥" + order.getMoney());
    }
}
