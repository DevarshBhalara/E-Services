package com.example.e_services;

public class WorkerProfileReviewModel {
    String uname,feedback,rating;

    public WorkerProfileReviewModel(String uname, String feedback, String rating) {
        this.uname = uname;
        this.feedback = feedback;
        this.rating = rating;
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
