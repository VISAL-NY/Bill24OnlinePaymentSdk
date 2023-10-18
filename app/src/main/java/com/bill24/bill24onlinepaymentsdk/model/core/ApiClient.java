package com.bill24.bill24onlinepaymentsdk.model.core;

import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.resonseModel.CheckoutDetailResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiClient {
    @POST("checkout/detail")
    Call<CheckoutDetailResponseModel> postCheckoutDetail(@Header("token") String token,
                                                         @Body CheckoutDetailRequestModel requestModel);

   // @POST("transaction/favorite")

}
