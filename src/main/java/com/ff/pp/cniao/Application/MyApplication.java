package com.ff.pp.cniao.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ff.pp.cniao.tools.UserLocalData;
import com.ff.pp.cniao.bean.User;

/**
 * Created by PP on 2017/3/27.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context mContext;

    private static MyApplication mInstance;
    private User mUser;
    private String mToken;
    private Intent mIntent;
    private int mRequestCode = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = getApplicationContext();
        mInstance = this;
        mUser = UserLocalData.getUser();
        mToken = UserLocalData.getToken();
    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public User getUser() {
        if (mUser == null)
            mUser = UserLocalData.getUser();
        return mUser;
    }

    public void putUser(User user) {
        mUser = user;
        UserLocalData.putUser(user);

    }

    public void putUserAndToken(User user) {
        putUser(user);
        putToken(user.getToken());
    }

    public String getToken() {
        if (mToken == null)
            mToken = UserLocalData.getToken();
        return mToken;
    }

    public void putToken(String token) {
        mToken = token;
        UserLocalData.putToken(mToken);
    }

    private void clearToken() {
        mToken = null;
        UserLocalData.clearToken();
        Log.e(TAG, "clearToken: getToken() " + getToken());
    }

    public void clearUserAndToken() {
        clearToken();
        mUser = null;
        UserLocalData.clearUser();
    }

    public void putFinalIntent(Intent intent) {
        mIntent = intent;
    }

    public Intent getFinalIntent() {
        return mIntent;
    }

    public void startActivityOfFinalIntent(Context context) {
        if (mRequestCode == -1) {
            context.startActivity(mIntent);

        }else {
            ((Activity)context).startActivityForResult(mIntent,mRequestCode);
            mRequestCode=-1;
        }
        mIntent = null;
    }

    public void putFinalIntent(Intent intent, int requestCode) {
        mIntent = intent;
        mRequestCode = requestCode;
    }
}
