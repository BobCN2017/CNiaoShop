package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.Campaigns;

import java.util.List;

/**
 * Created by PP on 2017/3/24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static final int ITEM_TYPE_LEFT = 0;
    private static final int ITEM_TYPE_RIGHT = 1;
    private LayoutInflater mLayoutInflater;
    //    private List<HomeCategory> mData;
    private List<Campaigns> mData;
    private Context mContext;
    private OnItemClickListener mListener;

//    public HomeAdapter(Context context, List<HomeCategory> mData) {
//        mLayoutInflater = LayoutInflater.from(context);
//        this.mData = mData;
//    }

    public HomeAdapter(Context context, List<Campaigns> mNetData) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.mData = mNetData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (viewType == ITEM_TYPE_LEFT)
            itemView = mLayoutInflater.inflate(R.layout.home_item_bigimage_left,
                    parent, false);
        else
            itemView = mLayoutInflater.inflate(R.layout.home_item_bigimage_right,
                    parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Campaigns item = mData.get(position);
        holder.textTitle.setText(item.getTitle());

        Glide.with(mContext).load(item.getCpOne().getImgUrl()).into(holder.bigImage);
        Glide.with(mContext).load(item.getCpTwo().getImgUrl()).into(holder.smallTopImage);
        Glide.with(mContext).load(item.getCpThree().getImgUrl()).into(holder.smallBottomImage);
//        Picasso.with(mContext).load(item.getCpOne().getImgUrl()).into(holder.bigImage);
//        Picasso.with(mContext).load(item.getCpTwo().getImgUrl()).into(holder.smallTopImage);
//        Picasso.with(mContext).load(item.getCpThree().getImgUrl()).into(holder.smallBottomImage);

//        holder.bigImage.setImageResource(item.getBigImageId());
//        holder.smallTopImage.setImageResource(item.getSmallTopImageId());
//        holder.smallBottomImage.setImageResource(item.getSmallButtonImageId());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return ITEM_TYPE_RIGHT;

        return ITEM_TYPE_LEFT;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, Campaigns.Campaign campaignNumber);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textTitle;
        private ImageView bigImage, smallTopImage, smallBottomImage;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            bigImage = (ImageView) itemView.findViewById(R.id.image_big);
            smallBottomImage = (ImageView) itemView.findViewById(R.id.imageSmallBottom);
            smallTopImage = (ImageView) itemView.findViewById(R.id.imageSmallTop);
            bigImage.setOnClickListener(this);
            smallBottomImage.setOnClickListener(this);
            smallTopImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Campaigns campaign=mData.get(getLayoutPosition());
            switch (v.getId()){
                case R.id.image_big:
                    mListener.onItemClick(v,campaign.getCpOne());
                    break;
                case R.id.imageSmallTop:
                    mListener.onItemClick(v,campaign.getCpTwo());
                    break;
                case R.id.imageSmallBottom:
                    mListener.onItemClick(v,campaign.getCpThree());
                break;

            }
        }
    }
}
