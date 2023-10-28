package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class BankPaymentMethodItemModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKh() {
        return nameKh;
    }

    public void setNameKh(String nameKh) {
        this.nameKh = nameKh;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isSupportDeeplink() {
        return supportDeeplink;
    }

    public void setSupportDeeplink(boolean supportDeeplink) {
        this.supportDeeplink = supportDeeplink;
    }

    public boolean isSupportCheckoutPage() {
        return supportCheckoutPage;
    }

    public void setSupportCheckoutPage(boolean supportCheckoutPage) {
        this.supportCheckoutPage = supportCheckoutPage;
    }

    public BankPaymentMethodItemModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private  String name;
    @SerializedName("name_kh")
    private  String nameKh;
    @SerializedName("logo")
    private  String logo;
    @SerializedName("is_favorite")
    private  boolean isFavorite;
    @SerializedName("fee")
    private  double fee;
    @SerializedName("support_deeplink")
    private  boolean supportDeeplink;
    @SerializedName("support_checkout_page")
    private  boolean supportCheckoutPage;
}
