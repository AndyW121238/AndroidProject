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

import com.study.food.R;
import com.study.food.dao.UserDao;
import com.study.food.model.User;
import com.study.food.utils.Tips;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText usernameEdit=null;
    EditText passwordEdit=null;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");
        usernameEdit=findViewById(R.id.loginUsernameEdit);
        passwordEdit=findViewById(R.id.loginPasswordEdit);

        findViewById(R.id.registerBtn).setOnClickListener(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    Tips.show("注册成功");
                    usernameEdit.setText("");
                    passwordEdit.setText("");
                    break;
                case 0x12:
                    Tips.show((String)msg.obj);
                    break;
                default:
                    Tips.show("网络连接错误,注册失败");
                    break;
            }
        }
    };
    //登录按钮点击事件
    void register() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if(username.isEmpty()){
            Tips.show("账号不能为空");
            return;
        } else if (password.isEmpty()) {
            Tips.show("密码不能为空");
            return;
        }
        User newUser = new User(username, password);
        new Thread(()->{
            User user= UserDao.selectUser(username);
            Message message=handler.obtainMessage();
            if(user==null){
                UserDao.insertUser(newUser);
                message.what=0x11;
            }else if(user.getUsername().equals(newUser.getUsername())){
                message.what=0x12;
                message.obj="账号已经存在";
            }
            //发送信息
            handler.sendMessage(message);
        }).start();
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.registerBtn){
            register();
        }
    }
}