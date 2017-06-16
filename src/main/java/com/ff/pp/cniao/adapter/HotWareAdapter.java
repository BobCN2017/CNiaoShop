package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.DraweeView;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.tools.WareInCartProvider;


import java.util.List;

/**
 * Created by PP on 2017/3/28.
 */

public class HotWareAdapter extends BaseAdapter<Ware> {
    private WareInCartProvider mProvider;

    public HotWareAdapter(Context context, List<Ware> list) {
        super(context, list, R.layout.hot_ware_item);
        mProvider = new WareInCartProvider();
    }

    @Override
    public void bindData(BaseViewHolder holder, final Ware ware) {
        DraweeView draweeView = (DraweeView) holder.getView(R.id.drawee_ware);
        draweeView.setImageURI(Uri.parse(ware.getImgUrl()));

        holder.getTextView(R.id.text_ware_title).setText(ware.getName());
        holder.getTextView(R.id.text_ware_price).setText("¥" + ware.getPrice());
        if (holder.getButton(R.id.imageButton_purchase) != null)
            holder.getButton(R.id.imageButton_purchase).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProvider.put(ware);
                    T.showTips("已放入购物车");
                }
            });
    }

    public void resetLayoutItemId(int layoutItemId) {
        mItemViewId = layoutItemId;
//        notifyItemRangeChanged(0,getItemCount());
//        notifyDataSetChanged();
    }
}
