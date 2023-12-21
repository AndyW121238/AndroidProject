package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Bean.Order;
import com.example.myapplication.Bean.Snack;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.imagecache.ImageUtils;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener{
    //订单信息
    Order order;
    ImageView back;
    ImageView imageView;
    TextView detailText;
    TextView priceText;
    TextView timeText;
    TextView evaluateText;
    public static void actionStart(Context context, Order order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        order= (Order) getIntent().getSerializableExtra("order");
        back=findViewById(R.id.orderDetailBack);
        imageView=findViewById(R.id.orderDetailImage);
        detailText=findViewById(R.id.orderDetail);
        priceText=findViewById(R.id.orderDetailPrice);
        timeText=findViewById(R.id.orderDetailTime);
        evaluateText=findViewById(R.id.evaluateText);
        back.setOnClickListener(this);
        imageView.setOnClickListener(this);
        initView();
    }
    public void initView(){
        ImageUtils.setImageBitmap(imageView,order.getImage(),this);
        detailText.setText("名称："+order.getName()+"，价格："+order.getMoney()/order.getCount());
        priceText.setText("数量："+order.getCount()+"，总价："+order.getMoney());
        timeText.setText("下单时间："+order.getTime()+"\n订单号："+order.getId());
        if(order.getEvaluate()==null){
            evaluateText.setText("还没评价");
        }else {
            evaluateText.setText(order.getEvaluate());
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.orderDetailBack){
            finish();
        }else if(v.getId()==R.id.orderDetailImage){
            List<Snack> snackList= MyApplication.getSnackList();
            Snack snack = null;
            for(Snack snack1 : snackList){
                if(snack1.getName().equals(order.getName())){
                    snack=snack1;
                    break;
                }
            }
            SnackDetailActivity.actionStart(this,snack);
        }
    }
}
