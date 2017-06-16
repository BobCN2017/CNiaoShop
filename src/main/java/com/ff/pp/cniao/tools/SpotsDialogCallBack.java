package com.ff.pp.cniao.tools;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

import com.ff.pp.cniao.LoginActivity;


/**
 * Created by PP on 2017/3/25.
 */

public abstract class  SpotsDialogCallBack<T> extends BaseCallBack<T> {

    SpotsDialog dialog;
    private Context mContext;

    public SpotsDialogCallBack(Context context) {
        mContext=context;
        dialog=new SpotsDialog(context);
    }

    private void showDialog(){
        dialog.show();
    }

    private void dismissDialog(){
        dialog.dismiss();
    }

    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }


    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
        e.printStackTrace();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onTokenError(Response response, int code) {
        com.ff.pp.cniao.tools.T.showTips("用户令牌错误，请重新登录");
        Intent intent=new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }
}
