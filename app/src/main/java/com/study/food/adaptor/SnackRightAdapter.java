package com.study.food.adaptor;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.study.food.R;
import com.study.food.model.Snack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnackRightAdapter extends BaseQuickAdapter<Snack, BaseViewHolder> {

    public SnackRightAdapter(List<Snack> snacks) {
        super(R.layout.item_snack_right, snacks);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Snack snack) {
        baseViewHolder.setImageResource(R.id.snackRightImage, snack.getImage())
                .setText(R.id.snackRightName, snack.getName())
                .setText(R.id.snackRightPrice, "￥" + snack.getPrice());
    }
}
