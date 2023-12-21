package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Bean.Order;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.dao.OrderDao;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.utils.Tips;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView evaluateBack=null;
    ImageView imageView=null;
    TextView textView=null;
    EditText evaluateEdit=null;
    String evaluateContent=null;
    Button submitEvaluate=null;
    Order order=null;

    public static void actionStart(Context context, Order order) {
        Intent intent = new Intent(context, EvaluateActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        order= (Order) getIntent().getSerializableExtra("order");
        evaluateBack=findViewById(R.id.evaluateBack);
        imageView=findViewById(R.id.evaluateImage);
        textView=findViewById(R.id.evaluateName);
        evaluateEdit=findViewById(R.id.evaluateContent);
        submitEvaluate=findViewById(R.id.submitEvaluateBtn);
        evaluateBack.setOnClickListener(this);
        submitEvaluate.setOnClickListener(this);
        if(order!=null) {
            //设置图片
            ImageUtils.setImageBitmap(imageView, order.getImage(), this);
            textView.setText(order.getName());
        }
    }
    public void submitEvaluate(){
        evaluateContent=evaluateEdit.getText().toString();
        if(evaluateContent.isEmpty()){
            Tips.show("评价内容不能为空");
            return;
        }
        new Thread(()->{
            boolean b=OrderDao.updateEvaluate(order.getId(), evaluateContent);
            Message message=handler.obtainMessage();
            message.what=0x11;
            message.obj=b;
            handler.sendMessage(message);
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    boolean b=(boolean) msg.obj;
                    if(!b){
                        Tips.show("网络连接错误");
                    }else {
                        Tips.show("评价成功");
                        MyApplication.setOrderEvaluate(order.getId(),evaluateContent);
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.evaluateBack) {
            finish();
        } else if (v.getId()==R.id.submitEvaluateBtn) {
            submitEvaluate();
        }
    }
}
