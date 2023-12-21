package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.activity.SnackDetailActivity;
import com.example.myapplication.adaptor.HomeAdapter;
import com.example.myapplication.MyAnimation;
import com.example.myapplication.Bean.Snack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment {
    RecyclerView homeRecyclerView=null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 首页瀑布流列表
        homeRecyclerView= requireActivity().findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initHomeAdapter();
    }
    public List<Snack> randomSnack(){
        int i;
        List<Snack> snackList= MyApplication.getSnackList();
        if(snackList==null){
            return null;
        }
        List<Snack> homeSnack=new ArrayList<>();
        Random random=new Random();
        int size=snackList.size();
        int temp=random.nextInt(size);
        homeSnack.add(snackList.get(temp));
        for (i=0;i<8;i++){
            temp=random.nextInt(size);
            if(!homeSnack.contains(snackList.get(temp))){
                homeSnack.add(snackList.get(temp));
            }else {
                i--;
            }
        }
        return homeSnack;
    }
    public void initHomeAdapter() {
        // 实例化购物车列表适配器对象
        //HomeAdapter adapter = new HomeAdapter(DataServer.getHomeList());
        HomeAdapter adapter = new HomeAdapter(randomSnack());
        // 设置动画效果
        adapter.setAnimationEnable(true);
        adapter.setAdapterAnimation(new MyAnimation());
        // 设置头部
        adapter.setHeaderView(getHeadView(), 1);
        // 设置尾部
        adapter.setFooterView(getFooterView(), 1);
        // 点击事件监听器
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Snack snack = (Snack) adapter1.getItem(position);
            SnackDetailActivity.actionStart(getContext(), snack);
        });
        // 设置适配器
        homeRecyclerView.setAdapter(adapter);
    }
    //首页RecyclerView头部View
    public View getHeadView() {
        return getLayoutInflater().inflate(R.layout.head_home_image, homeRecyclerView, false);
    }

    //首页RecyclerView尾部View
    public View getFooterView() {
        return getLayoutInflater().inflate(R.layout.footer_no_item, homeRecyclerView, false);
    }
}