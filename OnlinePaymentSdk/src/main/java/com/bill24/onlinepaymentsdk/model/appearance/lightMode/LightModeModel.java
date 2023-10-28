package com.bill24.onlinepaymentsdk.model.appearance.lightMode;
import com.bill24.onlinepaymentsdk.model.appearance.ButtonModel;
import com.bill24.onlinepaymentsdk.model.appearance.PrimaryColorModel;
import com.bill24.onlinepaymentsdk.model.appearance.SecondaryColorModel;
import com.google.gson.annotations.SerializedName;

public class LightModeModel {
    public PrimaryColorModel getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(PrimaryColorModel primaryColor) {
        this.primaryColor = primaryColor;
    }

    public SecondaryColorModel getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(SecondaryColorModel secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public ButtonModel getButton() {
        return button;
    }

    public void setButton(ButtonModel button) {
        this.button = button;
    }

    @SerializedName("primary_color")
    private PrimaryColorModel primaryColor;
    @SerializedName("secondary_color")
    private SecondaryColorModel secondaryColor;

    public String getLabelBackgroundColor() {
        return labelBackgroundColor;
    }

    public void setLabelBackgroundColor(String labelBackgroundColor) {
        this.labelBackgroundColor = labelBackgroundColor;
    }

    @SerializedName("label_background_color")
    private String labelBackgroundColor;
    @SerializedName("button")
    private ButtonModel button;
}
