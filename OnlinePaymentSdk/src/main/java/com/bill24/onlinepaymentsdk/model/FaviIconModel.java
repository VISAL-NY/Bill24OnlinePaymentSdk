package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class FaviIconModel {
    public String getIcon16x16() {
        return icon16x16;
    }

    public void setIcon16x16(String icon16x16) {
        this.icon16x16 = icon16x16;
    }

    public String getIcon32x32() {
        return icon32x32;
    }

    public void setIcon32x32(String icon32x32) {
        this.icon32x32 = icon32x32;
    }

    public String getIcon96x96() {
        return icon96x96;
    }

    public void setIcon96x96(String icon96x96) {
        this.icon96x96 = icon96x96;
    }

    @SerializedName("icon_16x16")
    private String icon16x16;
    @SerializedName("icon_32x32")
    private  String icon32x32;
    @SerializedName("icon_96x96")
    private  String icon96x96;
}
