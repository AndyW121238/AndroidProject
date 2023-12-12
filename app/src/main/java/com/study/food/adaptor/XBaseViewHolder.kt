package com.study.food.adaptor

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class XBaseViewHolder(view: View) : BaseViewHolder(view) {

    fun getItemPosition(): Int {
        return layoutPosition
    }

    fun getItemViewsType(): Int {
        return itemViewType
    }
}