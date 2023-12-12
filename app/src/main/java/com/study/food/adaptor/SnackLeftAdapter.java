package com.study.food.adaptor;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.study.food.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnackLeftAdapter extends BaseQuickAdapter<String, XBaseViewHolder> {

    public SnackLeftAdapter(List<String> types) {
        super(R.layout.item_snack_left, types);
    }

    /**
     * 设置item数据
     */
    @Override
    protected void convert(@NotNull XBaseViewHolder baseViewHolder, String s) {
        // 第一个item默认选中状态
//        if (baseViewHolder.getLayoutPosition() == 0) {
//            baseViewHolder.setBackgroundResource(R.id.snackLeftType, R.color.colorBgWhite);
//        }
        if (baseViewHolder.getItemPosition() == 0) {
            baseViewHolder.setBackgroundResource(R.id.snackLeftType, R.color.colorBgWhite);
        }
        baseViewHolder.setText(R.id.snackLeftType, s);
    }

}