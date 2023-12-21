package com.example.myapplication.imagecache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageUtils {
    //异步设置显示图片
    public static void setImageBitmap(ImageView ivImg, String imagUrl, Context context){
        MyBitmapUtils myBitmapUtils = new MyBitmapUtils(context);
        Bitmap imageFile = myBitmapUtils.disPlay(ivImg, imagUrl);
        CacheImageBean imageGrid = new CacheImageBean();
        imageGrid.setBitmapFile(imageFile);
        imageGrid.setGridImage(ivImg);
        Message msg = Message.obtain();
        msg.obj = imageGrid;
        msg.what = 100;
        handler.sendMessage(msg);
    }
    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    CacheImageBean obj = (CacheImageBean) msg.obj;
                    obj.getGridImage().setImageBitmap(obj.getBitmapFile());
                    break;
            }
        }
    };
}
