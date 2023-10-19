package com.bill24.bill24onlinepaymentsdk.model.requestModel;

import com.google.gson.annotations.SerializedName;

public class ExpiredRequestModel {
    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public ExpiredRequestModel(String tranId) {
        this.tranId = tranId;
    }

    @SerializedName("tran_id")
    private String tranId;
}
