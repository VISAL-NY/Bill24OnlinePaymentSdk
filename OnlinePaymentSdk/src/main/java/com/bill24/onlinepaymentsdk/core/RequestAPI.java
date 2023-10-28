package com.bill24.onlinepaymentsdk.core;



import com.bill24.onlinepaymentsdk.model.AddToFavoriteModel;
import com.bill24.onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.onlinepaymentsdk.model.GenerateLinkDeepLinkModel;
import com.bill24.onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.onlinepaymentsdk.model.conts.Constant;
import com.bill24.onlinepaymentsdk.model.requestModel.AddToFavoriteRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.bill24.onlinepaymentsdk.model.requestModel.GenerateDeeplinkRequestModel;

import retrofit2.Call;

public class RequestAPI {

    private final String refererKey;
    public RequestAPI(String refererKey){
        this.refererKey=refererKey;
    }

    public Call<BaseResponse<CheckoutDetailModel>> postCheckoutDetail(CheckoutDetailRequestModel model){
        return RetrofitClient.getInstance().getApiClient()
                .postCheckoutDetail(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,model);
    }

    public  Call<BaseResponse<ExpiredTransactionModel>> postExpireTran(ExpiredRequestModel model){
        return  RetrofitClient.getInstance().getApiClient().
                        postExpiredTransaction(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,model);
    }
    public Call<BaseResponse<GenerateLinkDeepLinkModel>> postGenerateDeeplink(GenerateDeeplinkRequestModel model){
        return RetrofitClient.getInstance().getApiClient().
                postGenerateDeepLink(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,model);
    }
    public Call<BaseResponse<AddToFavoriteModel>> postAddToFavorite(AddToFavoriteRequestModel model){
        return RetrofitClient.getInstance().getApiClient().
                postAddToFavorite(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,model);
    }
}
