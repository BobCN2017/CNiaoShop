package com.ff.pp.cniao.bean;

import java.io.Serializable;

/**
 * Created by PP on 2017/4/11.
 */

public class User implements Serializable{

    public static final int STATE_LOGIN_SUCCESS = 1;
    public static final int STATE_FAILED = 0;
    /**
     * token : 17f69b88-4681-4e10-8b66-fb4903d9be3c
     * data : {"id":275675,"email":"zhangp365@gmail.com","username":"东城狮","mobi":"13957328305"}
     * status : 1
     * message : success
     */

    private String token;

    private UserInfo data;
    private int status;
    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return data;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.data = userInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class UserInfo implements Serializable{
        /**
         * id : 275675
         * email : zhangp365@gmail.com
         * username : 东城狮
         * mobi : 13957328305
         */

        private int id;
        private String email;
        private String username;
        private String mobi;
        private String logo_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobi() {
            return mobi;
        }

        public void setMobi(String mobi) {
            this.mobi = mobi;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

    }
}
