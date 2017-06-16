package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.facebook.drawee.view.DraweeView;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.myapplication2.R;

import java.util.List;

/**
 * Created by PP on 2017/3/28.
 */

public class CategoryWareAdapter extends BaseAdapter<Ware> {

    private static final String TAG = "CategoryWareAdapter";
    private Context mContext;

    public CategoryWareAdapter(Context context, List<Ware> list) {
        super(context, list, R.layout.category_wares_grid_item);
        mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder holder, Ware ware) {
        DraweeView draweeView= (DraweeView) holder.getView(R.id.drawee_ware);
        Log.e(TAG, "bindData: "+ware.getImgUrl() );
        draweeView.setImageURI(Uri.parse(ware.getImgUrl().trim()));
//        Glide.with(mContext).load(ware.getImgUrl()).into(holder.getImageView(R.id.drawee_ware));
        holder.getTextView(R.id.text_ware_title).setText(ware.getName());
        holder.getTextView(R.id.text_ware_price).setText("¥" + ware.getPrice());
    }
}
