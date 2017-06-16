package com.ff.pp.cniao.tools;

import android.text.TextUtils;

import com.ff.pp.cniao.bean.User;

/**
 * Created by PP on 2017/4/12.
 */

public class UserLocalData {

    public static void putUser(User user) {
        String json = GsonUtil.gson.toJson(user);
        PreferencesUtil.putString(Constants.KEY_USER_JSON, json);
    }

    public static User getUser() {
        String json = PreferencesUtil.getString(Constants.KEY_USER_JSON);
        if (!TextUtils.isEmpty(json))
            return GsonUtil.gson.fromJson(json, User.class);
        return null;
    }

    public static void putToken(String token) {
        PreferencesUtil.putString(Constants.KEY_TOKEN, token);
    }

    public static String getToken() {
        return PreferencesUtil.getString(Constants.KEY_TOKEN);
    }


    public static void clearUser() {
        PreferencesUtil.putString(Constants.KEY_USER_JSON, "");
    }

    public static void clearToken() {
        PreferencesUtil.putString(Constants.KEY_TOKEN, "");
    }

}
