package com.example.e_services;

import android.content.Context;

public class TestModel {
    String wid;
    Context ctx;

    public TestModel(String wid, Context ctx) {
        this.wid = wid;
        this.ctx = ctx;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
