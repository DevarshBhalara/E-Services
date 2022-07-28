package com.example.e_services;

public class WorkerNewBookModel {
    private String wid;
    private String uid;
    private String wname;
    private String uname;
    private String mobile_number;
    private String datework;
    private String total;
    private String address;
    public WorkerNewBookModel(String wid, String uid, String wname, String uname, String mobile_number, String datework,String total) {

        this.wid = wid;
        this.uid = uid;
        this.wname = wname;
        this.uname = uname;
        this.mobile_number = mobile_number;
        this.datework = datework;
        this.total = total;
    }

    public WorkerNewBookModel(String wid, String uid, String wname, String uname, String mobile_number, String datework, String total, String address) {
        this.wid = wid;
        this.uid = uid;
        this.wname = wname;
        this.uname = uname;
        this.mobile_number = mobile_number;
        this.datework = datework;
        this.total = total;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDatework() {
        return datework;
    }

    public void setDatework(String datework) {
        this.datework = datework;
    }


}
