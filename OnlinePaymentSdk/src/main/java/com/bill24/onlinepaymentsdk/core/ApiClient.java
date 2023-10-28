package com.bill24.onlinepaymentsdk.core;


import com.bill24.onlinepaymentsdk.model.AddToFavoriteModel;
import com.bill24.onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.onlinepaymentsdk.model.GenerateLinkDeepLinkModel;
import com.bill24.onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.onlinepaymentsdk.model.requestModel.AddToFavoriteRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.GenerateDeeplinkRequestModel;

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
            @Body CheckoutDetailRequestModel requestModel);
    @POST("checkout/generate_links")
    Call<BaseResponse<GenerateLinkDeepLinkModel>> postGenerateDeepLink(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Body GenerateDeeplinkRequestModel requestModel
    );

    @POST("checkout/extend_expire_date")
    Call<BaseResponse<ExpiredTransactionModel>> postExpiredTransaction(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Body ExpiredRequestModel requestModel
            );
    @POST("checkout/favorite_bank")
    Call<BaseResponse<AddToFavoriteModel>> postAddToFavorite(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Body AddToFavoriteRequestModel requestModel
    );


}
