package com.ff.pp.cniao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PP on 2017/4/25.
 */

public class Order implements Serializable,Comparable{

    /**
     * items : [{"wares":{"id":1,"categoryId":5,"campaignId":1,"name":"联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg","price":5979,"sale":8654},"id":4142,"orderId":2143,"ware_id":1},{"wares":{"id":2,"categoryId":6,"campaignId":1,"name":"奥林巴斯（OLYMPUS）E-M10-1442-EZ 微单电电动变焦套机 银色 内置WIFI 翻转触摸屏 EM10复古高雅","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_rBEhWlMFnG0IAAAAAAIqnbSuyAAAAIxLwJ57aQAAiq1705.jpg","price":3799,"sale":3020},"id":4143,"orderId":2143,"ware_id":2}]
     * id : 2143
     * userId : 275675
     * orderNum : 20170418195908000100
     * createdTime : Apr 18, 2017 7:59:08 PM
     * amount : 9778
     * status : 1
     */

    private int id;
    private int userId;
    private String orderNum;
    private String createdTime;
    private int amount;
    private int status;
    private List<ItemsBean> items;

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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return ((Order) o).getOrderNum().compareTo(getOrderNum());
    }

    public static class ItemsBean {
        /**
         * wares : {"id":1,"categoryId":5,"campaignId":1,"name":"联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg","price":5979,"sale":8654}
         * id : 4142
         * orderId : 2143
         * ware_id : 1
         */

        private Ware wares;
        private int id;
        private int orderId;
        private int ware_id;

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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getWare_id() {
            return ware_id;
        }

        public void setWare_id(int ware_id) {
            this.ware_id = ware_id;
        }

    }
}
