package com.example.myapplication.adaptor;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.Bean.Snack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//下单页面购物车列表适配器
public class PlaceOrderAdapter extends BaseQuickAdapter<Snack, BaseViewHolder> {

    public PlaceOrderAdapter(List<Snack> snacks) {
        super(R.layout.item_place_order, snacks);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Snack snack) {
        baseViewHolder.setText(R.id.placeOrderName, snack.getName());
        baseViewHolder.setText(R.id.placeOrderPrice, "￥" + snack.getPrice());
        baseViewHolder.setText(R.id.orderCountBtn, String.valueOf(snack.getCount()));
        ImageView image=baseViewHolder.findView(R.id.placeOrderImage);
        ImageUtils.setImageBitmap(image,snack.getImage(),getContext());
    }
}
