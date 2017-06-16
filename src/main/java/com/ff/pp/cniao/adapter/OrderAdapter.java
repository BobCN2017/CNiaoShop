package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.bean.Order;
import com.ff.pp.cniao.bean.WareInCart;
import com.ff.pp.cniao.view.NumberAddSubView;
import com.ff.pp.myapplication2.R;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

/**
 * Created by PP on 2017/3/30.
 */

public class OrderAdapter extends BaseAdapter<Order> {
    private static final String TAG = "OrderAdapter";
    private static final int ORDER_STATUS_SUCCESS = 1;
    private static final int ORDER_STATUS_FAIL = -2;
    private static final int ORDER_STATUS_WAIT = 0;
    private OnWareStateChangedListener mListener;

    public OrderAdapter(Context context, List<Order> list) {
        super(context, list, R.layout.order_info_item);

    }

    public void setOnWareStateChangedListener(OnWareStateChangedListener listener) {
        this.mListener = listener;
    }

    @Override
    public void bindData(BaseViewHolder holder, final Order order) {

        holder.getTextView(R.id.textView_order_number).setText("订单号："+order.getOrderNum());
        holder.getTextView(R.id.textView_order_amount).setText("订单金额：¥"+order.getAmount());
        NineGridImageView nineGridImageView= (NineGridImageView) holder.getView(R.id.nineGrid_order_wares);
        nineGridImageView.setAdapter(new NiniGridImage());
        nineGridImageView.setImagesData(order.getItems());


        holder.getButton(R.id.button_buy_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.getButton(R.id.button_comment_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changeTextViewAndButtonByStatus(holder.getTextView(R.id.textView_order_status)
                ,holder.getButton(R.id.button_buy_again)
                , holder.getButton(R.id.button_comment_order)
                ,order.getStatus());
    }

    private void changeTextViewAndButtonByStatus(TextView textStatus, Button buyAgain
            , Button comment, int status) {
        switch (status){
            case ORDER_STATUS_SUCCESS:
                textStatus.setText("成功");
                textStatus.setTextColor(Color.GREEN);
                buyAgain.setText("再次购买");
                comment.setText("评价订单");
                comment.setVisibility(View.VISIBLE);
                break;
            case ORDER_STATUS_FAIL:
                textStatus.setText("支付失败");
                textStatus.setTextColor(Color.RED);
                buyAgain.setText("再次付款");
                comment.setVisibility(View.GONE);

                break;
            case ORDER_STATUS_WAIT:
                textStatus.setText("等待付款");
                textStatus.setTextColor(Color.RED);
                buyAgain.setText("立即支付");
                comment.setVisibility(View.GONE);

                break;

        }

    }


    public interface OnWareStateChangedListener{
        void wareCountChanged(WareInCart ware, int count);
        void wareSelectedChanged(WareInCart ware, boolean selected);
    }

    class NiniGridImage extends NineGridImageViewAdapter<Order.ItemsBean> {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, Order.ItemsBean item) {
            Glide.with(MyApplication.getContext()).load(item.getWares().getImgUrl()).into(
                    imageView);
        }
    }
}
