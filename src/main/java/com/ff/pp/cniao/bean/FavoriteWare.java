package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/4/26.
 */

public class FavoriteWare {

    /**
     * wares : {"id":219,"categoryId":4,"campaignId":8,"name":"海尔HaierEMS70BZ58W 7公斤 变频全自动波轮洗衣机 免清洗 双动力","imgUrl":"http://m.360buyimg.com/n4/jfs/t1246/325/997499216/35783/22c1e62d/55652a83Ncdc26074.jpg!q70.jpg","price":2899,"sale":1908}
     * id : 445
     * userId : 275675
     * wareId : 219
     * createTime : Apr 26, 2017 10:29:28 AM
     */

    private Ware wares;
    private int id;
    private int userId;
    private int wareId;
    private String createTime;

    public Ware getWares() {
        return wares;
    }

    public void setWares(Ware wares) {
        this.wares = wares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWareId() {
        return wareId;
    }

    public void setWareId(int wareId) {
        this.wareId = wareId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
