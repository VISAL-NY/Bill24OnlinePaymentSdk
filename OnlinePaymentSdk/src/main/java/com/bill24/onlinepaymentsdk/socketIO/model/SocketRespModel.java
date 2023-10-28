package com.bill24.onlinepaymentsdk.socketIO.model;

import com.google.gson.annotations.SerializedName;

public class SocketRespModel {

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    @SerializedName("transactionId")
    private String tranNo;
}
