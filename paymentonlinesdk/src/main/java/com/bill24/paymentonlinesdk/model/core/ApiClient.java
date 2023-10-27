package com.bill24.paymentonlinesdk.model.core;

import com.bill24.paymentonlinesdk.model.AddToFavoriteModel;
import com.bill24.paymentonlinesdk.model.CheckoutDetailModel;
import com.bill24.paymentonlinesdk.model.ExpiredTransactionModel;
import com.bill24.paymentonlinesdk.model.GenerateLinkDeepLinkModel;
import com.bill24.paymentonlinesdk.model.baseResponseModel.BaseResponse;
import com.bill24.paymentonlinesdk.model.requestModel.AddToFavoriteRequestModel;
import com.bill24.paymentonlinesdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.paymentonlinesdk.model.requestModel.ExpiredRequestModel;
import com.bill24.paymentonlinesdk.model.requestModel.GenerateDeeplinkRequestModel;

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
    @POST("checkout/favorite")
    Call<BaseResponse<AddToFavoriteModel>> postAddToFavorite(
            @Header("Content-Type") String contentType,
            @Header("token") String token,
            @Header("Referer-Key") String refererKey,
            @Body AddToFavoriteRequestModel requestModel
    );


}
