package com.ff.pp.cniao.adapter;

import android.content.Context;

import android.util.Log;

import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.view.NumberAddSubView;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.WareInCart;

import java.util.List;

/**
 * Created by PP on 2017/3/30.
 */

public class CartAdapter extends BaseAdapter<WareInCart> {
    private static final String TAG = "CartAdapter";

    private OnWareStateChangedListener mListener;

    public CartAdapter(Context context, List<WareInCart> list) {
        super(context, list, R.layout.cart_ware_item);

    }

    public void setOnWareStateChangedListener(OnWareStateChangedListener listener) {
        this.mListener = listener;
    }

    @Override
    public void bindData(BaseViewHolder holder, final WareInCart ware) {
        Log.e(TAG, "bindData: " );
        holder.getTextView(R.id.textView_title_cart).setText(ware.getName());
        holder.getTextView(R.id.textView_price_cart).setText("¥" + ware.getPrice());

        Glide.with(MyApplication.getContext()).load(ware.getImgUrl()).into(
                holder.getImageView(R.id.imageView_ware_cart));

        holder.getCheckBox(R.id.checkbox_cart_item).setChecked(ware.isChecked());
        holder.getCheckBox(R.id.checkbox_cart_item).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.wareSelectedChanged(ware,isChecked);
            }
        });

        NumberAddSubView numberAddSubView = (NumberAddSubView)
                holder.getView(R.id.number_add_sub_view);
        numberAddSubView.setValue(ware.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                mListener.wareNumberChanged(ware,value);

            }

            @Override
            public void onButtonSubClick(View view, int value) {
                mListener.wareNumberChanged(ware,value);
            }
        });
    }


    public interface OnWareStateChangedListener{
        void wareNumberChanged(WareInCart ware, int count);
        void wareSelectedChanged(WareInCart ware,boolean selected);
    }
}
