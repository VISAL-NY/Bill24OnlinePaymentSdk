package com.bill24.onlinepaymentsdk.model.requestModel;

import com.google.gson.annotations.SerializedName;

public class CheckoutDetailRequestModel {
    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public CheckoutDetailRequestModel(String tranId) {
        this.tranId = tranId;
    }

    @SerializedName("tran_id")
    private String tranId;
}
