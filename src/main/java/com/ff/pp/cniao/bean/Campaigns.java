package com.ff.pp.cniao.bean;

/**
 * Created by PP on 2017/3/26.
 */

public class Campaigns {

    /**
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg"}
     * id : 1
     * title : 超值购
     * campaignOne : 17
     * campaignTwo : 15
     * campaignThree : 11
     */

    private Campaign cpOne;
    private Campaign cpTwo;
    private Campaign cpThree;
    private int id;
    private String title;

    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Campaign {
        /**
         * id : 17
         * title : 手机专享
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
         */

        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
