package com.ff.pp.cniao.tools;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WareInCart;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PP on 2017/3/30.
 */

public class WareInCartProvider {

    private static final String TAG = "WareInCartProvider";
    public static final String KEY_WARES_LIST = "key_wares_list";
    private SparseArray<WareInCart> mWares;

    public static Gson gson = new Gson();

    public WareInCartProvider() {
        mWares = getDataFromDisk();
    }

    public void put(Ware ware) {
        WareInCart wareInCart = mWares.get(ware.getId());
        if (wareInCart != null) {
            wareInCart.setCount(wareInCart.getCount() + 1);
        } else {
            wareInCart = new WareInCart(ware);
        }
        mWares.put(wareInCart.getId(), wareInCart);
        saveDataToDisk();

    }

    public void update(WareInCart ware) {
        mWares.put(ware.getId(), ware);
        saveDataToDisk();
    }

    public void delete(Ware ware) {
        mWares.remove(ware.getId());

        saveDataToDisk();
    }

    public void removeWares(List<WareInCart> list) {
        for (WareInCart ware:list){
            delete(ware);
        }
        Log.e(TAG, "removeWares mWares.size(): "+mWares.size() );
    }

    public void setAllSelected(boolean isChecked) {
        for (int i = 0; i < mWares.size(); i++) {
            mWares.valueAt(i).setChecked(isChecked);
        }
        saveDataToDisk();
    }


    public List<WareInCart> getAllSelected(){
      List<WareInCart> list=new ArrayList<>();

        List<WareInCart> all=getAll();
        for (WareInCart ware:all){
            if (ware.isChecked()){
                list.add(ware);
            }
        }
        return list;
    }

    public List<WareInCart> getAll() {
        List<WareInCart> list = getListFromDisk();
        mWares=listToSparseArray(list);
        return list;
    }

    public Double countTotal() {
        mWares=getDataFromDisk();
        Double total=0d;
        for (int i = 0; i < mWares.size(); i++) {
            WareInCart ware=mWares.valueAt(i);
            if(ware.isChecked()){
                total+=ware.getPrice()*ware.getCount();
            }
        }
        return total;
    }

    public boolean isAllSelected() {
        mWares=getDataFromDisk();
        for (int i = 0; i < mWares.size(); i++) {
            WareInCart ware=mWares.valueAt(i);
            if(!ware.isChecked()){
                return false;
            }
        }
        return true;
    }


    private SparseArray<WareInCart> getDataFromDisk() {
        List<WareInCart> list = getListFromDisk();
        return listToSparseArray(list);
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
        List<WareInCart> list = sparseArrayToList(mWares);
        PreferencesUtil.putString(KEY_WARES_LIST, gson.toJson(list));

    }

    private List<WareInCart> sparseArrayToList(SparseArray<WareInCart> array) {
        List<WareInCart> wares = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            wares.add(array.get(array.keyAt(i)));
        }
        return wares;
    }

    private SparseArray<WareInCart> listToSparseArray(List<WareInCart> list) {
        SparseArray<WareInCart> wares = new SparseArray<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null)
                wares.put(list.get(i).getId(), list.get(i));
        }
        return wares;
    }
}
