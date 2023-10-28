package com.bill24.onlinepaymentsdk.model.requestModel;

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

    public GenerateDeeplinkRequestModel(String bankId, String transactionNo) {
        this.bankId = bankId;
        this.transactionNo = transactionNo;
    }

    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("transaction_id")
    private String transactionNo;
}
