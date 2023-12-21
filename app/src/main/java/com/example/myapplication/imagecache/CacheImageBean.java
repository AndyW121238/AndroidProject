package com.example.myapplication.imagecache;

import android.graphics.Bitmap;
import android.widget.ImageView;

//图片缓存bean
public class CacheImageBean {
    public Bitmap bitmapFile;
    public ImageView gridImage;
    public CacheImageBean() {
    }

    public Bitmap getBitmapFile() {
        return bitmapFile;
    }

    public void setBitmapFile(Bitmap bitmapFile) {
        this.bitmapFile = bitmapFile;
    }

    public ImageView getGridImage() {
        return gridImage;
    }

    public void setGridImage(ImageView gridImage) {
        this.gridImage = gridImage;
    }
}
