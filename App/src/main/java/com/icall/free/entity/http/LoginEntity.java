package com.icall.free.entity.http;

import java.util.Date;

public class LoginEntity extends BaseEntity {
    private Integer uid;
    private String token;
    private String token_expried;
    private String phone;
    private String name;
    private String photo;
    private String mcc;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_expried() {
        return token_expried;
    }

    public void setToken_expried(String token_expried) {
        this.token_expried = token_expried;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
}
