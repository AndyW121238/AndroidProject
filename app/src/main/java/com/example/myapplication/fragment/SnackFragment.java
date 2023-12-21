package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.activity.SnackDetailActivity;
import com.example.myapplication.adaptor.SnackLeftAdapter;
import com.example.myapplication.adaptor.SnackRightAdapter;
import com.example.myapplication.Bean.Snack;
import com.example.myapplication.utils.Tips;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SnackFragment extends Fragment {

    // 小吃页面左边列表已选择的Position
    private int leftSelectPosition = 0;
    RecyclerView leftRecyclerview = null;
    RecyclerView rightRecyclerView = null;
    // 右边适配器
    List<String> snackTypes=MyApplication.getSnackTypes();
    List<Snack> snackList=MyApplication.getSnackList();
    private SnackRightAdapter rightAdapter;
    public static SnackFragment newInstance() {
        return new SnackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snack, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //绑定监听器
        leftRecyclerview=requireActivity().findViewById(R.id.orderLeftRecyclerView);
        rightRecyclerView=requireActivity().findViewById(R.id.orderRightRecyclerView);
        leftRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initLeftAdapter();
        initRightAdapter();
    }
    //初始化左边适配器
    @SuppressLint("ResourceAsColor")
    private void initLeftAdapter() {
        if(snackTypes==null){
            Tips.show("网络错误");
            return;
        }
        // 实例化左边适配器对象
        SnackLeftAdapter leftAdapter = new SnackLeftAdapter(snackTypes);
        // 设置动画效果
        leftAdapter.setAnimationEnable(true);
        leftAdapter.setAnimationFirstOnly(false);
        leftAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        // 触发点击按钮
        leftAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position != leftSelectPosition) {
                // 原本选中的item变成未选中颜色
                Objects.requireNonNull(adapter.getViewByPosition(leftSelectPosition, R.id.snackLeftType)).setBackgroundResource(R.color.colorContent);
                // 当前item变成选中颜色
                Objects.requireNonNull(adapter.getViewByPosition(position, R.id.snackLeftType)).setBackgroundResource(R.color.colorBgWhite);
                leftSelectPosition = position;
                rightAdapter.setAnimationEnable(false);
                //分类别
                List<Snack> itemSnacks=new ArrayList<>();
                for(Snack snack : snackList){
                    if(snack.getType().equals(snackTypes.get(position))){
                        itemSnacks.add(snack);
                    }
                }
                rightAdapter.setNewInstance(itemSnacks);
            }
        });
        // 设置左边列表适配器
        leftRecyclerview.setAdapter(leftAdapter);
    }

    //初始化右边适配器
    public void initRightAdapter() {
        if(snackList==null){
            Tips.show("网络错误");
            return;
        }
        // 实例化右边适配器对象
        List<Snack> selectSnacks=new ArrayList<>();
        for (Snack snack : snackList){
            if(snack.getType().equals(snackTypes.get(leftSelectPosition))){
                selectSnacks.add(snack);
            }
        }
        rightAdapter = new SnackRightAdapter(selectSnacks);
        // 设置动画效果
        rightAdapter.setAnimationEnable(true);
        rightAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight);
        // 设置尾部
        rightAdapter.addFooterView(getFooterView());
        // 点击item事件
        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Snack snack = (Snack) adapter.getItem(position);
                SnackDetailActivity.actionStart(getContext(), snack);
            }
        });
        // 左边列表加入购物车点击事件
        rightAdapter.addChildClickViewIds(R.id.snackRightAddBtn);
        rightAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Snack snack = (Snack) adapter.getItem(position);
            if (view.getId() == R.id.snackRightAddBtn) {
                if (!MyApplication.getCartSnacks().contains(snack)) {
                    // 添加到购物车
                    snack.setCount(1);
                    MyApplication.getCartSnacks().add(snack);
                    Tips.show("已添加" + snack.getName() + "到购物车");
                } else {
                    Tips.show("已在购物车中，不能重复添加");
                }
            }
        });
        // 设置右边列表适配器
        rightRecyclerView.setAdapter(rightAdapter);
    }

    //小吃页面右边RecyclerView尾部View
    private View getFooterView() {
        return getLayoutInflater().inflate(R.layout.footer_no_item, rightRecyclerView, false);
    }
}