package com.example.e_services;

public class UserHistoryModel {
    String uname,wname,w_mono,wid,uid,datebook,total,rating;

    public UserHistoryModel(String uname, String wname, String w_mono, String wid, String uid, String datebook, String total) {
        this.uname = uname;
        this.wname = wname;
        this.w_mono = w_mono;
        this.wid = wid;
        this.uid = uid;
        this.datebook = datebook;
        this.total = total;
    }

    public UserHistoryModel(String wname, String uname, String w_mono, String datebook, String wid) {
        this.uname = uname;
        this.wname = wname;
        this.w_mono = w_mono;
        this.datebook = datebook;
        this.wid = wid;
    }
    public UserHistoryModel(String wname, String uname, String w_mono, String datebook, String wid,String rating) {
        this.uname = uname;
        this.wname = wname;
        this.w_mono = w_mono;
        this.datebook = datebook;
        this.wid = wid;
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getW_mono() {
        return w_mono;
    }

    public void setW_mono(String w_mono) {
        this.w_mono = w_mono;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDatebook() {
        return datebook;
    }

    public void setDatebook(String datebook) {
        this.datebook = datebook;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
