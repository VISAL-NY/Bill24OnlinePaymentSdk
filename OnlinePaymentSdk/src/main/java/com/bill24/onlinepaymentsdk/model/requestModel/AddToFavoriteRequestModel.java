package com.bill24.onlinepaymentsdk.model.requestModel;

import com.google.gson.annotations.SerializedName;

public class AddToFavoriteRequestModel {
    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public AddToFavoriteRequestModel(String tranId, String bankId, boolean isFavorite) {
        this.tranId = tranId;
        this.bankId = bankId;
        this.isFavorite = isFavorite;
    }

    @SerializedName("tran_id")
    private String tranId;
    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("is_favorite")
    private  boolean isFavorite;
}
