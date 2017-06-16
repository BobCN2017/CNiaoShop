package com.ff.pp.cniao.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.LoginActivity;

/**
 * Created by PP on 2017/4/12.
 */

public class BaseFragment extends Fragment {


    public void startActivityAfterCheckLogin(Intent intent) {
        if (TextUtils.isEmpty(MyApplication.getInstance().getToken())) {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
            MyApplication.getInstance().putFinalIntent(intent);
        } else {
            startActivity(intent);
        }
    }

    public void startActivityForResultAfterCheckLogin( Intent intent, int requestCode) {
        if (TextUtils.isEmpty(MyApplication.getInstance().getToken())) {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            startActivity(loginIntent);
            MyApplication.getInstance().putFinalIntent(intent, requestCode);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }
}
