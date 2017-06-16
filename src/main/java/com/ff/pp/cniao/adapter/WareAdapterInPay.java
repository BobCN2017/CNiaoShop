package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.DraweeView;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WareInCart;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.tools.WareInCartProvider;
import com.ff.pp.myapplication2.R;

import java.util.List;

/**
 * Created by PP on 2017/3/28.
 */

public class WareAdapterInPay extends BaseAdapter<WareInCart> {

    private List<WareInCart> mList;
    public WareAdapterInPay(Context context, List<WareInCart> list) {
        super(context, list, R.layout.ware_in_pay_item);

        mList=list;
    }

    @Override
    public void bindData(BaseViewHolder holder, final WareInCart ware) {
        DraweeView draweeView = (DraweeView) holder.getView(R.id.drawee_ware_in_pay);
        draweeView.setImageURI(Uri.parse(ware.getImgUrl()));
        if (draweeView!= null)
            draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //TODO 去商品详情
                }
            });
    }

    public float getWaresTotalCost(){
        float total=0;
        for (WareInCart ware:mList){
            total+=ware.getPrice();
        }
        return total;
    }
}
