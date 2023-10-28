package com.bill24.onlinepaymentsdk.model.baseResponseModel;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageKh() {
        return messageKh;
    }

    public void setMessageKh(String messageKh) {
        this.messageKh = messageKh;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("message_kh")
    private String messageKh;
    @SerializedName("data")
    private T data;
}
