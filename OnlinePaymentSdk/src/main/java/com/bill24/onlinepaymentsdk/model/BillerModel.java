package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

public class BillerModel {
    @SerializedName("biller_code")
    private String billerCode;
    @SerializedName("biller_name")
    private String billerName;
    @SerializedName("biller_name_kh")
    private String billerNameKh;
    @SerializedName("biller_display_name")
    private  String  billerDisplayName;
    @SerializedName("biller_logo")
    private  String billerLogo;
}
