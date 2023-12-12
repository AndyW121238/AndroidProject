package com.study.food.ui.place;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.study.food.MyApplication;
import com.study.food.R;
import com.study.food.activity.DetailActivity;
import com.study.food.adaptor.PlaceOrderAdapter;
import com.study.food.dao.OrderDao;
import com.study.food.dao.UserDao;
import com.study.food.model.Order;
import com.study.food.model.Snack;
import com.study.food.model.User;
import com.study.food.utils.Tips;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment implements View.OnClickListener{
    private PlaceViewModel placeViewModel;
    RecyclerView orderRecyclerView;
    TextView placeMoney = null;
    PlaceOrderAdapter orderAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderRecyclerView=requireActivity().findViewById(R.id.placeRecyclerView);
        placeMoney=requireActivity().findViewById(R.id.placeMoney);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //点击监听
        requireActivity().findViewById(R.id.placeBuyBtn).setOnClickListener(this);
        requireActivity().findViewById(R.id.deleteOrder).setOnClickListener(this);
        initOrderAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次页面显示都计算合计金额
        calcTotalMoney();
    }
    //初始化购物车列表适配器
    @SuppressLint("NotifyDataSetChanged")
    private void initOrderAdapter() {
        // 实例化购物车列表适配器对象
        orderAdapter = new PlaceOrderAdapter(MyApplication.getCartSnacks());
        // 设置空布局
        orderAdapter.setEmptyView(getEmptyView());
        // 设置动画效果
        orderAdapter.setAnimationEnable(true);
        orderAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        // 点击item事件触发
        orderAdapter.setOnItemClickListener((adapter, view, position) -> {
            Snack snack = (Snack) adapter.getItem(position);
            DetailActivity.actionStart(getContext(), snack);
        });

        // 注册item内子控件id
        orderAdapter.addChildClickViewIds(R.id.orderLessLabel, R.id.orderAddLabel);
        // 子控件点击监听
        orderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Snack snack = (Snack) adapter.getItem(position);
            if(view.getId()==R.id.orderLessLabel){
                // 点击减少数量
                if (snack.getCount() == 1) {
                    MyApplication.getCartSnacks().remove(position);
                } else {
                    MyApplication.getCartSnacks().get(position).setCount(snack.getCount() - 1);
                }
                adapter.notifyDataSetChanged();
            } else if (view.getId()==R.id.orderAddLabel) {
                // 点击添加数量
                MyApplication.getCartSnacks().get(position).setCount(snack.getCount() + 1);
                adapter.notifyDataSetChanged();
            }
            calcTotalMoney();
        });
        // 设置适配器
        orderRecyclerView.setAdapter(orderAdapter);
    }

    //点击下单按钮事件触发器
    void initClick() {
        if (MyApplication.getCartSnacks().isEmpty()) {
            Tips.show("购物车是空的啦！！！");
        } else {
            if (MyApplication.getLogin()) {
                // 显示Dialog
                showDialog();
            } else {
                Tips.show("请先登录");
            }
        }
    }
    //显示下单备注提示框
    @SuppressLint({"InflateParams", "NotifyDataSetChanged"})
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_view, null))
                .setTitle("备注")
                .setPositiveButton("下单", (dialog, which) -> {
                    // 持久化订单数据
                    saveOrder();
                    // 清空购物车数据
                    MyApplication.getCartSnacks().removeAll(MyApplication.getCartSnacks());
                    // 通知适配器数据变化
                    orderAdapter.notifyDataSetChanged();
                    // 刷新总金额
                    calcTotalMoney();
                    Tips.show("下单成功");
                })
                .create()
                .show();
    }

    //持久化订单数据
    public void saveOrder() {
        //OrderDao.saveOrder(orders);
        new Thread(()->{
            List<Order> orders = new ArrayList<>();
            // 购物车数据产生订单
            for (Snack snack : MyApplication.getCartSnacks()) {
                Order order = new Order(snack,MyApplication.getUser().getUsername());
                order.setUsername(MyApplication.getUser().getUsername());
                orders.add(order);
            }
            OrderDao.insertOrder(orders);
        }).start();
    }

    //点击垃圾桶事件触发器
    void deleteOrder() {
        if (MyApplication.getCartSnacks().isEmpty()) {
            Tips.show("购物车是空的");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示")
                    .setMessage("是否清空购物车？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        // 清空购物车数据
                        MyApplication.getCartSnacks().removeAll(MyApplication.getCartSnacks());
                        // 通知适配器数据变化
                        orderAdapter.notifyDataSetChanged();
                        // 刷新总金额
                        calcTotalMoney();
                        Tips.show("已清空购物车");
                    })
                    .create()
                    .show();
        }
    }

    // 计算合计金额
    @SuppressLint("SetTextI18n")
    private void calcTotalMoney() {
        BigDecimal totalMoney = BigDecimal.valueOf(0);

        // 遍历计算总金额（解决舍入误差）
        if (!MyApplication.getCartSnacks().isEmpty()) {
            for (Snack snack : MyApplication.getCartSnacks()) {
                // 小吃单价 × 小吃数量
                BigDecimal tmp = BigDecimal.valueOf(snack.getPrice()).multiply(BigDecimal.valueOf(snack.getCount()));
                totalMoney = totalMoney.add(tmp);
            }
        }

        placeMoney.setText("￥" + totalMoney.doubleValue());
    }

    //下单页面购物车空布局
    private View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.empty_cart_view, orderRecyclerView, false);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.placeBuyBtn){
            initClick();
        } else if (v.getId()==R.id.deleteOrder) {
            deleteOrder();
        }
    }
}