package com.ff.pp.cniao.bean;

import java.io.Serializable;

/**
 * Created by PP on 2017/3/24.
 */

public class BaseBean implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
