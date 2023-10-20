package com.bill24.bill24onlinepaymentsdk.model.core;

import com.bill24.bill24onlinepaymentsdk.model.AddToFavoriteModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.bill24onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.bill24onlinepaymentsdk.model.GenerateLinkDeepLinkModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.AddToFavoriteRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.GenerateDeeplinkRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiClient {
    @POST("checkout/detail")
    Call<BaseResponse<CheckoutDetailModel>> postCheckoutDetail(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Header("language") String language,
            @Body CheckoutDetailRequestModel requestModel);
    @POST("checkout/generatelinks")
    Call<BaseResponse<GenerateLinkDeepLinkModel>> postGenerateDeepLink(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Header("language") String language,
            @Body GenerateDeeplinkRequestModel requestModel
    );

    @POST("transaction/extend_expire_date")
    Call<BaseResponse<ExpiredTransactionModel>> postExpiredTransaction(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Header("language") String language,
            @Body ExpiredRequestModel requestModel
            );
    @POST("transaction/favorite")
    Call<BaseResponse<AddToFavoriteModel>> postAddToFavorite(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Header("language") String language,
            @Body AddToFavoriteRequestModel requestModel
    );


}
