package com.study.food.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.study.food.R;
import com.study.food.activity.DetailActivity;
import com.study.food.adaptor.HomeAdapter;
import com.study.food.animator.MyAnimation3;
import com.study.food.data.DataServer;
import com.study.food.model.Snack;



public class HomeFragment extends Fragment {
    RecyclerView homeRecyclerView=null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        // 绑定资源
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 首页瀑布流列表
        homeRecyclerView= requireActivity().findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initHomeAdapter();
    }

    public void initHomeAdapter() {
        // 实例化购物车列表适配器对象
        HomeAdapter adapter = new HomeAdapter(DataServer.getHomeList());
        // 设置动画效果
        adapter.setAnimationEnable(true);
        adapter.setAdapterAnimation(new MyAnimation3());
        // 设置头部
        adapter.setHeaderView(getHeadView(), 1);
        // 设置尾部
        adapter.setFooterView(getFooterView(), 1);
        // 点击事件监听器
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Snack snack = (Snack) adapter1.getItem(position);
            DetailActivity.actionStart(getContext(), snack);
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