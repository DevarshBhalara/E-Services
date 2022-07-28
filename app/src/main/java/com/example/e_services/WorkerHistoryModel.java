package com.example.e_services;

public class WorkerHistoryModel {
    String total,uid,wid,wname,uname,u_mono,prof,datework,status;

    public WorkerHistoryModel(String total,String uid, String wid, String wname, String uname, String u_mono, String prof, String datework, String status) {
        this.total = total;
        this.uid = uid;
        this.wid = wid;
        this.wname = wname;
        this.uname = uname;
        this.u_mono = u_mono;
        this.prof = prof;
        this.datework = datework;
        this.status = status;
    }

    public WorkerHistoryModel(String wname, String uname, String datework,String u_mono) {
        this.wname = wname;
        this.uname = uname;
        this.datework = datework;
        this.u_mono = u_mono;
    }
    public WorkerHistoryModel(String wname, String uname, String datework) {
        this.wname = wname;
        this.uname = uname;
        this.datework = datework;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
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

    public String getU_mono() {
        return u_mono;
    }

    public void setU_mono(String u_mono) {
        this.u_mono = u_mono;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getDatework() {
        return datework;
    }

    public void setDatework(String datework) {
        this.datework = datework;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
