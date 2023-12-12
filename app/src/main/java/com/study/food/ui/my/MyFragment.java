package com.study.food.ui.my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.study.food.MyApplication;
import com.study.food.R;
import com.study.food.activity.LoginActivity;
import com.study.food.activity.OrderActivity;
import com.study.food.dao.UserDao;
import com.study.food.model.User;
import com.study.food.utils.Tips;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Objects;

public class MyFragment extends Fragment implements View.OnClickListener{
    private MyViewModel myViewModel;
    CircleImageView image = null;
    TextView nickname = null;
    TextView username = null;
    LinearLayout modifyView = null;
    LinearLayout generalView = null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        // 绑定资源
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image= requireActivity().findViewById(R.id.myUserHead);
        nickname= requireActivity().findViewById(R.id.myUserNickName);
        username= requireActivity().findViewById(R.id.myUserName);
        modifyView= requireActivity().findViewById(R.id.myModifyView);
        generalView= requireActivity().findViewById(R.id.myGeneralView);
        //点击监听
        requireActivity().findViewById(R.id.myUserHead).setOnClickListener(this);
        requireActivity().findViewById(R.id.constraintLayout).setOnClickListener(this);
        requireActivity().findViewById(R.id.myOrderView).setOnClickListener(this);
        requireActivity().findViewById(R.id.myModifyText).setOnClickListener(this);
        requireActivity().findViewById(R.id.myGeneralText).setOnClickListener(this);
        requireActivity().findViewById(R.id.myModifyBtn).setOnClickListener(this);
        requireActivity().findViewById(R.id.myGeneralBtn).setOnClickListener(this);
        requireActivity().findViewById(R.id.logoutBtn).setOnClickListener(this);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        if (MyApplication.getLogin()) {
            User user = MyApplication.getUser();
            nickname.setText(user.getUsername());
            username.setText("账号: " + user.getUsername());
        }
    }

    /**
     * 点击头像
     */
    void clickImage() {
        if (MyApplication.getLogin()) {
            Tips.show("已登录");
        } else {
            LoginActivity.actionStart(requireActivity());
        }
    }
    void clickcslayout() {
        if (MyApplication.getLogin()) {
            Tips.show("已登录");
        } else {
            LoginActivity.actionStart(getActivity());
        }
    }

    /**
     * 点击我的订单
     */
    void clickOrder() {
        if (MyApplication.getLogin()) {
            OrderActivity.actionStart(getContext());
        } else {
            Tips.show("请先登录");
        }
    }
    void clickShowModify() {
        if (modifyView.getVisibility() == View.GONE) {
            modifyView.setVisibility(View.VISIBLE);
        } else {
            modifyView.setVisibility(View.GONE);
        }
    }
    void clickShowGeneral() {
        if (generalView.getVisibility() == View.GONE) {
            generalView.setVisibility(View.VISIBLE);
        } else {
            generalView.setVisibility(View.GONE);
        }
    }
    void clickModifySubmit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("是否保存地址信息")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modifyView.setVisibility(View.GONE);
                    }
                })
                .create()
                .show();
    }
    void clickGeneralSubmit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示")
                .setMessage("是否保存口味偏好")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        generalView.setVisibility(View.GONE);
                    }
                })
                .create()
                .show();
    }

    /**
     * 点击退出登录
     */
    void clickLogout() {
        if (MyApplication.getLogin()) {
            // 清除持久化数据
            UserDao.removeUserAndLoginStatus();
            // 清除全局数据
            MyApplication.setLogin(false);
            MyApplication.setUser(null);
            nickname.setText("未登录");
            username.setText("");
            image.setImageResource(R.mipmap.logo);
        } else {
            Tips.show("还没有登录，请先登录");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.myUserHead){
            clickImage();
        }else if(v.getId()==R.id.constraintLayout){
            clickcslayout();
        }else if(v.getId()==R.id.myOrderView){
            clickOrder();
        }else if(v.getId()==R.id.myModifyText){
            clickShowModify();
        }else if(v.getId()==R.id.myGeneralText){
            clickShowGeneral();
        }else if(v.getId()==R.id.myModifyBtn){
            clickModifySubmit();
        }else if(v.getId()==R.id.myGeneralBtn){
            clickGeneralSubmit();
        }else if(v.getId()==R.id.logoutBtn){
            clickLogout();
        }
    }
}