package com.example.myapplication.adaptor;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.Bean.Snack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnackRightAdapter extends BaseQuickAdapter<Snack, BaseViewHolder> {

    public SnackRightAdapter(List<Snack> snacks) {
        super(R.layout.item_snack_right, snacks);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Snack snack) {
        baseViewHolder.setText(R.id.snackRightName, snack.getName());
        baseViewHolder.setText(R.id.snackRightPrice, "￥" + snack.getPrice());
        ImageView image=baseViewHolder.findView(R.id.snackRightImage);
        ImageUtils.setImageBitmap(image,snack.getImage(),getContext());
    }
}
