package com.ff.pp.cniao.bean;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PP on 2017/4/21.
 */

public class Addressee implements Serializable,Comparable<Addressee>{
    private int userId;
    @SerializedName("consignee")
    private String name;

    private String phone;
    @SerializedName("addr")
    private String address;

    @SerializedName("isDefault")
    private boolean isDefaultAddress;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    @Override
    public int compareTo(@NonNull Addressee o) {
        return((Boolean) o.isDefaultAddress()).compareTo(isDefaultAddress);
    }
}
