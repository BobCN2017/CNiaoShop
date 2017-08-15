package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/8/15.
 */

public class WareChange {
    private Ware ware;
    private int change;

    public WareChange(Ware ware, int change) {
        this.ware = ware;
        this.change = change;
    }

    public Ware getWare() {
        return ware;
    }

    public void setWare(Ware ware) {
        this.ware = ware;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}
