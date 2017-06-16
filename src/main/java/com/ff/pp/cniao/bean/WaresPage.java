package com.ff.pp.cniao.bean;

import java.util.List;

/**
 * Created by PP on 2017/3/27.
 */

public class WaresPage<T> {

    /**
     * copyright : 本API接口只允许菜鸟窝(http://www.cniao5.com)用户使用,其他机构或者个人使用均为侵权行为
     * totalCount : 28
     * currentPage : 2
     * totalPage : 1
     * pageSize : 10
     * orders : []
     * list : [{"id":11,"categoryId":5,"campaignId":3,"name":"三星（SAMSUNG）S22B310B 21.5英寸宽屏LED背光液晶显示器","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_54695388N06e5b769.jpg","price":799,"sale":7145},{"id":12,"categoryId":1,"campaignId":6,"name":"希捷（seagate）Expansion 新睿翼1TB 2.5英寸 USB3.0 移动硬盘 (STEA1000400)","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5519fdd0N02706f5e.jpg","price":399,"sale":402},{"id":13,"categoryId":5,"campaignId":2,"name":"联想（ThinkCentre）E73S（10EN001ACD） 小机箱台式电脑 （I3-4160T 4GB 500GB 核显 Win7）23.8英寸显示器","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55558580Nbb8545f3.jpg","price":3599,"sale":571},{"id":14,"categoryId":5,"campaignId":4,"name":"新观 LED随身灯充电宝LED灯 LED护眼灯 阅读灯 下单备注颜色 无备注颜色随机发","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5563e1d4Ncc22964e.jpg","price":1,"sale":1652},{"id":15,"categoryId":5,"campaignId":2,"name":"旅行港版转换插头插座 港版充电器转换头 电源三转二","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5583931aN31c0a2cf.jpg","price":1,"sale":6547},{"id":16,"categoryId":5,"campaignId":13,"name":"极米 XGIMI Z4X LED投影仪 无屏电视 微型投影仪 家用","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1adc2Nf655de47.jpg","price":2699,"sale":7778},{"id":17,"categoryId":5,"campaignId":10,"name":"惠普（HP）超薄系列 HP14g-ad005TX 14英寸超薄笔记本（i5-5200U 4G 500G 2G独显 DVD刻录 蓝牙 win8.1）银色","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55f9280eN31523bd6.jpg","price":3299,"sale":9248},{"id":18,"categoryId":1,"campaignId":1,"name":"华硕（ASUS）经典系列X554LP 15.6英寸笔记本 （i5-5200U 4G 500G R5-M230 1G独显 蓝牙 Win8.1 黑色）","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5604aab9N7a91521b.jpg","price":2999,"sale":2906},{"id":19,"categoryId":5,"campaignId":2,"name":"海尔（Haier）云悦mini 2W(Win8.1)迷你主机(Intel四核J1900 4G 500G WIFI USB3.0 Win8.1)迷你电脑","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd53edbf09N01b79405.jpg","price":1699,"sale":6786},{"id":20,"categoryId":5,"campaignId":14,"name":"橙派（CPAD）E3 1231 V3/8G/GTX960-4G/游戏电脑主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55619f15Nf1e9c48f.jpg","price":3479,"sale":5211}]
     */

    private String copyright;
    private int totalCount;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    private List<?> orders;
    private List<T> list;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
