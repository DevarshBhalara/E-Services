package com.example.e_services;

public class SearchModel {
    private String id;
    private String wname;
    private String mobile_number;
    private String profession;


    public SearchModel(String id, String wname, String mobile_number, String profession) {
        this.id = id;
        this.wname = wname;
        this.mobile_number = mobile_number;
        this.profession = profession;
    }

    public SearchModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
