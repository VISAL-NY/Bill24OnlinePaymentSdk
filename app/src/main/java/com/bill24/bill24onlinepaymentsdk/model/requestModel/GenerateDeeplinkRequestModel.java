package com.bill24.bill24onlinepaymentsdk.model.requestModel;

import com.google.gson.annotations.SerializedName;

public class GenerateDeeplinkRequestModel {
    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public GenerateDeeplinkRequestModel(String bankId, String transactionId) {
        this.bankId = bankId;
        this.transactionNo = transactionId;
    }

    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("transaction_id")
    private String transactionNo;
}
