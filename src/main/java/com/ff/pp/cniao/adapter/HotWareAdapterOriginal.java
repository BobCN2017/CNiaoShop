package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ff.pp.myapplication2.R;

import com.ff.pp.cniao.bean.Ware;

import java.util.List;

/**
 * Created by PP on 2017/3/27.
 */

public class HotWareAdapterOriginal extends RecyclerView.Adapter<HotWareAdapterOriginal.ViewHolder> {
    private static final String TAG = "HotWareAdapterOriginal";
    private LayoutInflater mLayoutInflater;

    private List<Ware> mData;
    private Context mContext;
    private HomeAdapter.OnItemClickListener mListener;


    public HotWareAdapterOriginal(Context context, List<Ware> mNetData) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.mData = mNetData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.hot_ware_item,
                parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotWareAdapterOriginal.ViewHolder holder, int position) {
        Ware item = mData.get(position);
        holder.textTitle.setText(item.getName());
        holder.textPrice.setText("¥"+item.getPrice() + "");
//        Log.e(TAG, "onBindViewHolder: "+item.getImgUrl() );
//        Glide.with(mContext).load(item.getImgUrl()).into(holder.draweeView);
        holder.draweeView.setImageURI(Uri.parse(item.getImgUrl()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, Ware ware);
    }

    public void setOnItemClickListener(HomeAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textTitle, textPrice;
        private SimpleDraweeView draweeView;
        private Button imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_ware_title);
            textPrice = (TextView) itemView.findViewById(R.id.text_ware_price);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_ware);
            imageButton = (Button) itemView.findViewById(R.id.imageButton_purchase);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }
}

