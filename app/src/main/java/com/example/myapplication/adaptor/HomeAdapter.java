package com.example.myapplication.adaptor;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.imagecache.ImageUtils;
import com.example.myapplication.Bean.Snack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<Snack, BaseViewHolder> {

    public HomeAdapter(List<Snack> snacks) {
        super(R.layout.item_home_snack, snacks);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Snack snack) {
        ImageView image=baseViewHolder.findView(R.id.homeSnackImage);
        ImageUtils.setImageBitmap(image,snack.getImage(),getContext());
    }

}
