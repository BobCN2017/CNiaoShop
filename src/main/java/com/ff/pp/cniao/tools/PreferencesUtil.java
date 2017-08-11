package com.ff.pp.cniao.tools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ff.pp.cniao.Application.MyApplication;

/**
 * Created by PP on 2017/3/30.
 */

public class PreferencesUtil {
    private static SharedPreferences preferences;

    static {
        preferences= PreferenceManager.getDefaultSharedPreferences(
                MyApplication.getContext());

    }

    public static void putString(String key, String value){
        preferences.edit().putString(key,value).commit();

    }

    public static String getString(String key){
        return preferences.getString(key,null);
    }
}
