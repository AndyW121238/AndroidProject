package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.myapplication.dao.UserDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkLogin();
    }
    public void initView(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // 将每个菜单ID作为一组ID传递
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_snack, R.id.navigation_place, R.id.navigation_my).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void checkLogin(){
        // 检查登录状态
        if (UserDao.getLogin()) {
            // 已登录
            // 检查持久化数据
            MyApplication.setLogin(true);
            MyApplication.setUser(UserDao.getUser());
        } else {
            // 未登录
            MyApplication.setLogin(false);
            MyApplication.setUser(null);
        }
    }
}