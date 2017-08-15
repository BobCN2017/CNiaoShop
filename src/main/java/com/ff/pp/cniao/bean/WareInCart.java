package com.ff.pp.cniao.bean;

import java.io.Serializable;

/**
 * Created by PP on 2017/3/30.
 */

public class WareInCart extends Ware implements Serializable {

    private boolean isChecked;
    private int count;

    public WareInCart(Ware ware) {
        super(ware);
        count=1;
        isChecked=true;
    }

    public WareInCart(Ware ware, int count) {
        super(ware);
        this.count=count;
        isChecked=true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
