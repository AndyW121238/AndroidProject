package com.example.myapplication.adaptor;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnackLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SnackLeftAdapter(List<String> types) {
        super(R.layout.item_snack_left, types);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        if (getItemPosition(s)==0){
            //设置第一个item的背景颜色
            baseViewHolder.setBackgroundResource(R.id.snackLeftType, R.color.colorBgWhite);
        }
        baseViewHolder.setText(R.id.snackLeftType, s);
    }
}