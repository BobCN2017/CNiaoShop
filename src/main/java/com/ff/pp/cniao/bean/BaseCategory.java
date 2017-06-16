package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/3/24.
 */

public class BaseCategory extends BaseBean {

    protected String name;


    public BaseCategory(long id,String name) {
        this.id=id;
        this.name = name;
    }

    public BaseCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
