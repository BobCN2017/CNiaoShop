package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/3/20.
 */

public class Tab {
    private int textId;
    private int imageId;
    private Class fragment;

    public Tab(int textId, int imageId, Class fragment) {
        this.textId = textId;
        this.imageId = imageId;
        this.fragment = fragment;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
