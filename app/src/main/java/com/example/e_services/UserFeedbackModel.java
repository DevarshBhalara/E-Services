package com.example.e_services;

public class UserFeedbackModel {
    String wid,wname,uname,feedback,rating;

    public UserFeedbackModel(String wid, String wname, String uname, String feedback, String rating) {
        this.wid = wid;
        this.wname = wname;
        this.uname = uname;
        this.feedback = feedback;
        this.rating = rating;
    }

    public UserFeedbackModel(String uname, String feedback, String rating) {
        this.uname = uname;
        this.feedback = feedback;
        this.rating = rating;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
