package com.bill24.bill24onlinepaymentsdk.model.requestModel;

import com.google.gson.annotations.SerializedName;

public class GenerateDeeplinkRequestModel {
    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public GenerateDeeplinkRequestModel(String bankId, String transactionId) {
        this.bankId = bankId;
        this.transactionId = transactionId;
    }

    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("transaction_id")
    private String transactionId;
}
