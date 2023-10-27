package com.bill24.bill24onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class Setting {
    public boolean isDisplayFee() {
        return displayFee;
    }
    public void setDisplayFee(boolean displayFee) {
        this.displayFee = displayFee;
    }
    @SerializedName("display_fee")
    private boolean displayFee;
}
