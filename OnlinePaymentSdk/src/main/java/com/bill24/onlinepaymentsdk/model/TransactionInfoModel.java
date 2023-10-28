package com.bill24.onlinepaymentsdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TransactionInfoModel implements Serializable {
    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getTranAmountDisplay() {
        return tranAmountDisplay;
    }

    public void setTranAmountDisplay(String tranAmountDisplay) {
        this.tranAmountDisplay = tranAmountDisplay;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountDisplay() {
        return totalAmountDisplay;
    }

    public void setTotalAmountDisplay(String totalAmountDisplay) {
        this.totalAmountDisplay = totalAmountDisplay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getBankRefId() {
        return bankRefId;
    }

    public void setBankRefId(String bankRefId) {
        this.bankRefId = bankRefId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKhqrImage() {
        return khqrImage;
    }

    public void setKhqrImage(String khqrImage) {
        this.khqrImage = khqrImage;
    }

    public String getKhqrString() {
        return khqrString;
    }

    public void setKhqrString(String khqrString) {
        this.khqrString = khqrString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BankPaymentMethodModel> getBankPaymentMethod() {
        return bankPaymentMethod;
    }

    public void setBankPaymentMethod(List<BankPaymentMethodModel> bankPaymentMethod) {
        this.bankPaymentMethod = bankPaymentMethod;
    }

    public TransactionInfoModel(){}

    public TransactionInfoModel(String customerName,String currency,String khqrImage,String totalAmountDisplay){
        this.customerName=customerName;
        this.currency=currency;
        this.khqrImage=khqrImage;
        this.totalAmountDisplay=totalAmountDisplay;
    }
    @SerializedName("tran_id")
    private String tranId;
    @SerializedName("identity_code")
    private String identityCode;
    @SerializedName("tran_no")
    private  String tranNo;
    @SerializedName("tran_amount")
    private  double tranAmount;
    @SerializedName("tran_amount_display")
    private  String tranAmountDisplay;
    @SerializedName("total_amount")
    private  double totalAmount;
    @SerializedName("total_amount_display")
    private String totalAmountDisplay;
    @SerializedName("currency")
    private  String currency;

    public boolean isAllowFavorite() {
        return isAllowFavorite;
    }

    public void setAllowFavorite(boolean allowFavorite) {
        isAllowFavorite = allowFavorite;
    }

    @SerializedName("is_allow_favorite")
    private boolean isAllowFavorite;
    @SerializedName("customer_code")
    private  String customerCode;
    @SerializedName("customer_name")
    private  String customerName;
    @SerializedName("customer_email")
    private String customerEmail;
    @SerializedName("customer_phone")
    private  String customerPhone;
    @SerializedName("tran_date")
    private  String tranDate;
    @SerializedName("expired_date")
    private String expiredDate;
    @SerializedName("bank_ref_id")
    private String bankRefId;
    @SerializedName("status")
    private String status;
    @SerializedName("cancel_url")
    private  String cancelUrl;
    @SerializedName("redirect_url")
    private  String redirectUrl;
    @SerializedName("payment_link")
    private String paymentLink;
    @SerializedName("language")
    private String language;
    @SerializedName("khqr_image")
    private  String khqrImage;
    @SerializedName("khqr_string")
    private  String khqrString;
    @SerializedName("description")
    private String description;
    @SerializedName("bank_payment_method")
    private List<BankPaymentMethodModel> bankPaymentMethod;

}
