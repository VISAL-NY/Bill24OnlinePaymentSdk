package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class GenerateLinkDeepLinkModel {
    public String getWebPaymentLink() {
        return webPaymentLink;
    }

    public void setWebPaymentLink(String webPaymentLink) {
        this.webPaymentLink = webPaymentLink;
    }

    public String getMobileDeepLink() {
        return mobileDeepLink;
    }

    public void setMobileDeepLink(String mobileDeepLink) {
        this.mobileDeepLink = mobileDeepLink;
    }

    @SerializedName("web_payment_url")
    private String webPaymentLink;
    @SerializedName("mobile_deep_link")
    private String mobileDeepLink;

}
