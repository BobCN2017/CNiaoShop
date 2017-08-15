package com.ff.pp.cniao.tools;

import android.text.TextUtils;
import android.util.Log;

import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WareChange;
import com.ff.pp.cniao.bean.WareInCart;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PP on 2017/3/30.
 */

public class WareInCartProvider {

    private static final String TAG = "WareInCartProvider";
    public static final String KEY_WARES_LIST = "key_wares_list";
    private List<WareInCart> mWares;

    public static Gson gson = new Gson();

    private WareInCartProvider() {
        mWares = getListFromDisk();
    }

    public static WareInCartProvider getInstance() {
        return WareInCartProviderHolder.provider;
    }

    @Subscribe()
    public void handleEvent(WareChange wareChange) {
        Ware wareChanged = wareChange.getWare();
        WareInCart wareInCart = getWareInList(wareChanged);
        if (wareInCart == null && wareChange.getChange() > 0) {
            mWares.add(new WareInCart(wareChanged, wareChange.getChange()));
        }
        if (wareInCart != null
                && (wareChange.getChange() + wareInCart.getCount()) > 0)
            wareInCart.setCount(wareInCart.getCount() + wareChange.getChange());
        if (wareInCart != null
                && (wareChange.getChange() + wareInCart.getCount()) <= 0)
            delete(wareInCart);
        saveDataToDisk();
    }

    @Subscribe()
    public void handleEvent(List<WareChange> wareChanges) {
        for (WareChange wareChange:wareChanges){
            handleEvent(wareChange);
        }
    }

    private WareInCart getWareInList(Ware ware) {
        for (WareInCart wareInCart : mWares) {
            if (wareInCart.getId() == ware.getId())
                return wareInCart;
        }
        return null;
    }

    public void update(WareInCart ware) {
        if (ware == null) return;
        WareInCart wareInCart = getWareInList(ware);
        if (wareInCart == null)
            mWares.add(ware);
        else
            wareInCart = ware;
        saveDataToDisk();
    }

    public void delete(Ware ware) {
        int pos = getWarePosition(ware);
        if (pos == -1) return;
        mWares.remove(pos);
        saveDataToDisk();
    }

    private int getWarePosition(Ware ware) {
        for (int i = 0, n = mWares.size(); i < n; i++) {
            if (mWares.get(i).getId() == ware.getId())
                return i;
        }
        return -1;
    }

    public void setAllSelected(boolean isChecked) {
        for (int i = 0; i < mWares.size(); i++) {
            mWares.get(i).setChecked(isChecked);
        }
        saveDataToDisk();
    }


    public List<WareInCart> getAllSelected() {
        List<WareInCart> list = new ArrayList<>();

        for (WareInCart ware : mWares) {
            if (ware.isChecked()) {
                list.add(ware);
            }
        }
        return list;
    }

    public List<WareInCart> getAll() {

        return mWares;
    }

    public Double countTotal() {

        Double total = 0d;
        for (int i = 0; i < mWares.size(); i++) {
            WareInCart ware = mWares.get(i);
            if (ware.isChecked()) {
                total += ware.getPrice() * ware.getCount();
            }
        }
        return total;
    }

    public boolean isAllSelected() {

        for (int i = 0; i < mWares.size(); i++) {
            WareInCart ware = mWares.get(i);
            if (!ware.isChecked()) {
                return false;
            }
        }
        return true;
    }

    private List<WareInCart> getListFromDisk() {
        String json = PreferencesUtil.getString(KEY_WARES_LIST);
        Log.e(TAG, "getListFromDisk: " + json);
        List<WareInCart> list = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            try {
                list = gson.fromJson(json, new TypeToken<List<WareInCart>>() {
                }.getType());
            } catch (JsonParseException e) {
                Log.e(TAG, "getListFromDisk JsonParseException: " + e.getMessage());
            }
        }
        Iterator<WareInCart> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null)
                iterator.remove();
        }
        return list;
    }

    private void saveDataToDisk() {
        PreferencesUtil.putString(KEY_WARES_LIST, gson.toJson(mWares));
    }

    private static class WareInCartProviderHolder {
        private static WareInCartProvider provider = new WareInCartProvider();
    }
}
