package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PP on 2017/3/28.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "BaseAdapter";
    protected List<T> mData;
    protected Context mContext;
    protected int mItemViewId;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BaseAdapter(Context context,List<T> list, int itemViewId) {
        this.mData = list;
        this.mContext = context;
        this.mItemViewId = itemViewId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mItemViewId, null, false);
        Log.e(TAG, "onCreateViewHolder: "+mItemViewId );
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        T t = mData.get(position);
        bindData(holder, t);

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null &&holder.getAdapterPosition()
                        !=RecyclerView.NO_POSITION)
                    mListener.onItemClick(v, holder.getAdapterPosition());
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract void bindData(BaseViewHolder holder, T t);
}
