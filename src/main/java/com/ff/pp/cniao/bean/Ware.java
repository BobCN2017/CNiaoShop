package com.ff.pp.cniao.bean;

import java.io.Serializable;

/**
 * Created by PP on 2017/3/30.
 */

public  class Ware implements Serializable{
    /**
     * id : 11
     * categoryId : 5
     * campaignId : 3
     * name : 三星（SAMSUNG）S22B310B 21.5英寸宽屏LED背光液晶显示器
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_54695388N06e5b769.jpg
     * price : 799.0
     * sale : 7145
     */

    protected int id;
    protected int categoryId;
    protected int campaignId;
    protected String name;
    protected String imgUrl;
    protected double price;
    protected int sale;

    public Ware(Ware ware) {
        id=ware.getId();
        categoryId=ware.getCategoryId();
        campaignId=ware.getCampaignId();
        name=ware.getName();
        imgUrl= ware.getImgUrl();
        price=ware.getPrice();
        sale=ware.getSale();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
