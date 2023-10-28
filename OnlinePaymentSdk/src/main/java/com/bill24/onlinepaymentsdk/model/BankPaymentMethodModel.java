package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankPaymentMethodModel {

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionKh() {
        return sectionKh;
    }

    public void setSectionKh(String sectionKh) {
        this.sectionKh = sectionKh;
    }

    public List<BankPaymentMethodItemModel> getItems() {
        return items;
    }

    public void setItems(List<BankPaymentMethodItemModel> items) {
        this.items = items;
    }

    public BankPaymentMethodModel(String section, List<BankPaymentMethodItemModel> items) {
        this.section = section;
        this.items = items;
    }

    @SerializedName("section")
    private String section;
    @SerializedName("section_kh")
    private  String sectionKh;
    @SerializedName("items")
    private List<BankPaymentMethodItemModel> items;
}

