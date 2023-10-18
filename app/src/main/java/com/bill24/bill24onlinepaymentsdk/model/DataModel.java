package com.bill24.bill24onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class DataModel {
    public BillerModel getBiller() {
        return biller;
    }

    public void setBiller(BillerModel biller) {
        this.biller = biller;
    }

    public TransactionInfoModel getTransInfo() {
        return transInfo;
    }

    public void setTransInfo(TransactionInfoModel transInfo) {
        this.transInfo = transInfo;
    }

    public CheckoutPageConfigModel getCheckoutPageConfig() {
        return checkoutPageConfig;
    }

    public void setCheckoutPageConfig(CheckoutPageConfigModel checkoutPageConfig) {
        this.checkoutPageConfig = checkoutPageConfig;
    }

    @SerializedName("biller")
    private BillerModel biller;
    @SerializedName("trans_info")
    private  TransactionInfoModel transInfo;
    @SerializedName("checkout_page_config")
    private  CheckoutPageConfigModel checkoutPageConfig;
}
