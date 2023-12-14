package com.study.food.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.food.MyApplication;
import com.study.food.R;
import com.study.food.model.Snack;
import com.study.food.utils.Tips;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView image=null;
    TextView name=null;
    TextView price=null;
    TextView detail=null;
    Button addCart=null;
    ImageView favorite=null;
    public static void actionStart(Context context, Snack snack) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("snack", snack);
        context.startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fullScreen(this);
        Snack snack = (Snack) getIntent().getSerializableExtra("snack");
        image=findViewById(R.id.detailImage);
        name=findViewById(R.id.detailName);
        price=findViewById(R.id.detailPrice);
        detail=findViewById(R.id.detailContent);
        addCart=findViewById(R.id.detailAddCartBtn);
        favorite=findViewById(R.id.detailFavorite);

        findViewById(R.id.detailBack).setOnClickListener(this);
        findViewById(R.id.detailFavorite).setOnClickListener(this);
        if (snack != null) {
            image.setImageResource(snack.getImage());
            name.setText(snack.getName());
            price.setText("￥" + snack.getPrice());
            detail.setText(snack.getDetail());

            addCart.setOnClickListener(v -> {
                if (!MyApplication.getCartSnacks().contains(snack)) {
                    // 添加到购物车
                    snack.setCount(1);
                    MyApplication.getCartSnacks().add(snack);
                    Tips.show("已添加" + snack.getName() + "到购物车");
                    // 关闭Activity
                    finish();
                } else {
                    Tips.show("已在购物车中，不能重复添加");
                }
            });
        }
    }
    void clickBack() {
        finish();
    }
    void clickFavorite() {
        favorite.setImageResource(R.drawable.ic_baseline_favorite_24dp);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = ((Window) window).getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            window.setAttributes(attributes);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.detailBack){
            clickBack();
        }
        if(v.getId()==R.id.detailFavorite){
            clickFavorite();
        }
    }
}