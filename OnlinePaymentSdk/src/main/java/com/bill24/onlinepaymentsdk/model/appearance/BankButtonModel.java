package com.bill24.onlinepaymentsdk.model.appearance;

import com.google.gson.annotations.SerializedName;

public class BankButtonModel {
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextPrimary() {
        return textPrimary;
    }

    public void setTextPrimary(String textPrimary) {
        this.textPrimary = textPrimary;
    }

    public String getTextSecondary() {
        return textSecondary;
    }

    public void setTextSecondary(String textSecondary) {
        this.textSecondary = textSecondary;
    }

    public String getHoverBorder() {
        return hoverBorder;
    }

    public void setHoverBorder(String hoverBorder) {
        this.hoverBorder = hoverBorder;
    }

    @SerializedName("background_color")
    private String backgroundColor;
    @SerializedName("text_primary")
    private String textPrimary;
    @SerializedName("text_secondary")
    private String textSecondary;
    @SerializedName("hover_border")
    private String hoverBorder;
}
