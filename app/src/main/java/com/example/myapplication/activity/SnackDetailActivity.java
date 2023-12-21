package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.Bean.Snack;
import com.example.myapplication.utils.Tips;

public class SnackDetailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView image=null;
    TextView name=null;
    TextView price=null;
    TextView detail=null;
    Button addCart=null;
    ImageView favorite=null;
    public static void actionStart(Context context, Snack snack) {
        Intent intent = new Intent(context, SnackDetailActivity.class);
        intent.putExtra("snack", snack);
        context.startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_detail);
        Snack snack = (Snack) getIntent().getSerializableExtra("snack");
        image=findViewById(R.id.detailImage);
        name=findViewById(R.id.detailName);
        price=findViewById(R.id.detailPrice);
        detail=findViewById(R.id.detailContent);
        addCart=findViewById(R.id.detailAddCartBtn);
        //favorite=findViewById(R.id.detailFavorite);
        findViewById(R.id.detailBack).setOnClickListener(this);
        //findViewById(R.id.detailFavorite).setOnClickListener(this);
        if (snack != null) {
            //设置图片
            ImageUtils.setImageBitmap(image,snack.getImage(),this);
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
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.detailBack){
            clickBack();
        }
//        if(v.getId()==R.id.detailFavorite){
//            clickFavorite();
//        }
    }
}