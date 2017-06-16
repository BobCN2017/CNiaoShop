package com.ff.pp.cniao.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PP on 2017/3/28.
 */

public class  BaseViewHolder extends ViewHolder {

    protected View mItemView;

    protected SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
        mItemView=itemView;
    }

    public View getItemView() {
        return mItemView;
    }

    public TextView getTextView(int id){
        return findView(id);
    }

    public ImageView getImageView(int id){
        return findView(id);
    }

    public Button getButton(int id){
        return findView(id);
    }

    public CheckBox getCheckBox(int id){
        return findView(id);
    }

    public View getView(int id){
        return findView(id);
    }

    private <T extends View> T findView(int id){
        View view=mViews.get(id);
        if (view==null){
            view=mItemView.findViewById(id);
            mViews.put(id,view);
        }
        return (T) view;

    }


}
