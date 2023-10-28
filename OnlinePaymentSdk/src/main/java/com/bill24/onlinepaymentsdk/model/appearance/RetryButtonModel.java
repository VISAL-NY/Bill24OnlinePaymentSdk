package com.bill24.onlinepaymentsdk.model.appearance;

import com.google.gson.annotations.SerializedName;

public class RetryButtonModel {

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
    @SerializedName("background_color")
    private String backgroundColor;
    @SerializedName("text_color")
    private String textColor;
}
