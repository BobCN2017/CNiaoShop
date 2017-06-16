package com.ff.pp.cniao.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.DraweeView;
import com.ff.pp.cniao.bean.BaseMessage;
import com.ff.pp.cniao.bean.FavoriteWare;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.tools.T;

import com.ff.pp.myapplication2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by PP on 2017/3/28.
 */

public class FavoriteWareAdapter extends BaseAdapter<FavoriteWare> {
    private static final String TAG = "FavoriteWareAdapter";
    List<FavoriteWare> mData;

    public FavoriteWareAdapter(Context context, List<FavoriteWare> list) {
        super(context, list, R.layout.favorite_ware_item);
        mData=list;

    }

    @Override
    public void bindData(final BaseViewHolder holder, final FavoriteWare favoriteWare) {
        Ware ware=favoriteWare.getWares();
        DraweeView draweeView = (DraweeView) holder.getView(R.id.drawee_ware_favorite);
        draweeView.setImageURI(Uri.parse(ware.getImgUrl()));

        holder.getTextView(R.id.text_ware_title).setText(ware.getName());
        holder.getTextView(R.id.text_ware_price).setText("¥" + ware.getPrice());
        if (holder.getButton(R.id.button_delete_favorite) != null)
            holder.getButton(R.id.button_delete_favorite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteFavoriteInList(favoriteWare,holder.getAdapterPosition());
                    deleteFavoriteInServer(favoriteWare);
                    T.showTips("删除商品");
                }
            });

        if (holder.getButton(R.id.button_find_similar) != null)
            holder.getButton(R.id.button_find_similar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    T.showTips("找相似商品");
                }
            });
    }

    private void deleteFavoriteInServer(FavoriteWare favoriteWare) {
        String url=Constants.FAVORITE_DELETE_URL;
        Map<String,String> params=new HashMap<>(1);
        params.put("id",favoriteWare.getId()+"");
//        url+="?id="+favoriteWare.getId();
        OkHttpHelper.getInstance().post(url,params,
                new SpotsDialogCallBack<BaseMessage>(mContext) {
                    @Override
                    public void onSuccess(Response response, BaseMessage message) {
                        if (message.getStatus() == 1) {
                            Log.e(TAG, "onSuccess: 删除用户收藏成功");
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void deleteFavoriteInList(FavoriteWare favoriteWare,int position) {
        mData.remove(favoriteWare);
        notifyItemRemoved(position);
    }


}
