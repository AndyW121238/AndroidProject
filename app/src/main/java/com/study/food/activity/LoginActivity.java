package com.study.food.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.study.food.MyApplication;
import com.study.food.R;
import com.study.food.dao.UserDao;
import com.study.food.model.User;
import com.study.food.utils.Tips;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usernameEdit=null;
    EditText passwordEdit=null;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit=findViewById(R.id.loginUsernameEdit);
        passwordEdit=findViewById(R.id.loginPasswordEdit);
        setTitle("登录");
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.registerBtn).setOnClickListener(this);
        User user=UserDao.getUser();
        // 恢复账号
        if(user!=null) {
            String username = user.getUsername();
            usernameEdit.setText(username);
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginBtn){
            login();
        }else if(v.getId()==R.id.registerBtn){
            register();
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            switch (msg.what){
                case 0x11:
                    User user=(User) msg.obj;
                    if(user.getPassword().equals(password)){
                        Tips.show("登陆成功");
                        MyApplication.setLogin(true);
                        MyApplication.setUser(user);
                        // 持久化已登录用户数据
                        UserDao.saveUser(user);
                        UserDao.setLogin(true);
                        // 持久化账号，以便退出登录后不用再输入账号
                        UserDao.saveUsername(username);
                        // 关闭Activity
                        finish();
                    }else {
                        Tips.show("密码错误");
                        passwordEdit.setText("");
                    }
                    break;
                case 0x12:
                    Tips.show("账号错误");
                    break;
            }
        }
    };
    //登录点击事件
    void login(){
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if(username.isEmpty()){
            Tips.show("账号不能为空");
            return;
        } else if (password.isEmpty()) {
            Tips.show("密码不能为空");
            return;
        }
        new Thread(()->{
            User user=UserDao.selectUser(username);
            Message message=handler.obtainMessage();
            if(user!=null){
                message.what=0x11;
                message.obj=user;
            }
            else {
                message.what=0x12;
            }
            //发送信息
            handler.sendMessage(message);
        }).start();
    }
    //注册点击事件
    void register() {
        RegisterActivity.actionStart(this);
    }
}