package com.ff.pp.cniao.adapter;

import android.content.Context;

import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.CategoryListBean;

import java.util.List;

/**
 * Created by PP on 2017/3/28.
 */

public class CategoryListAdapter extends BaseAdapter<CategoryListBean> {
    public CategoryListAdapter( Context context,List<CategoryListBean> list) {
        super(context,list, R.layout.category_list_item);
    }

    @Override
    public void bindData(BaseViewHolder holder, CategoryListBean item) {
        holder.getTextView(R.id.textview_category_list).setText(item.getName());
    }
}
