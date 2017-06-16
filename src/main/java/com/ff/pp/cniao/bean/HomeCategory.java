package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/3/24.
 */

public class HomeCategory extends BaseCategory {

    private int bigImageId;
    private int smallTopImageId;
    private int smallButtonImageId;



    public HomeCategory(String name) {
        super(name);
    }

    public HomeCategory(long id, String name) {
        super(id, name);
    }

    public HomeCategory( String name, int bigImageId, int smallTopImageId, int smallButtonImageId) {
        super( name);
        this.bigImageId = bigImageId;
        this.smallTopImageId = smallTopImageId;
        this.smallButtonImageId = smallButtonImageId;
    }
    public HomeCategory(long id, String name, int bigImageId, int smallTopImageId, int smallButtonImageId) {
        super(id, name);
        this.bigImageId = bigImageId;
        this.smallTopImageId = smallTopImageId;
        this.smallButtonImageId = smallButtonImageId;
    }

    public int getBigImageId() {
        return bigImageId;
    }

    public void setBigImageId(int bigImageId) {
        this.bigImageId = bigImageId;
    }

    public int getSmallTopImageId() {
        return smallTopImageId;
    }

    public void setSmallTopImageId(int smallTopImageId) {
        this.smallTopImageId = smallTopImageId;
    }

    public int getSmallButtonImageId() {
        return smallButtonImageId;
    }

    public void setSmallButtonImageId(int smallButtonImageId) {
        this.smallButtonImageId = smallButtonImageId;
    }
}
