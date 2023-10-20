package com.bill24.bill24onlinepaymentsdk.model.core;

import com.bill24.bill24onlinepaymentsdk.model.AddToFavoriteModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.bill24onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.bill24onlinepaymentsdk.model.GenerateLinkDeepLinkModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.AddToFavoriteRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.GenerateDeeplinkRequestModel;

import retrofit2.Call;

public class RequestAPI {

    private final String refererKey;
    private final String language;
    public RequestAPI(String refererKey,String language){
        this.refererKey=refererKey;
        this.language=language;
    }

    public Call<BaseResponse<CheckoutDetailModel>> postCheckoutDetail(CheckoutDetailRequestModel model){
        return RetrofitClient.getInstance().getApiClient()
                .postCheckoutDetail(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,language,model);
    }

    public  Call<BaseResponse<ExpiredTransactionModel>> postExpireTran(ExpiredRequestModel model){
        return  RetrofitClient.getInstance().getApiClient().
                        postExpiredTransaction(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,language,model);
    }
    public Call<BaseResponse<GenerateLinkDeepLinkModel>> postGenerateDeeplink(GenerateDeeplinkRequestModel model){
        return RetrofitClient.getInstance().getApiClient().
                postGenerateDeepLink(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,language,model);
    }
    public Call<BaseResponse<AddToFavoriteModel>> postAddToFavorite(AddToFavoriteRequestModel model){
        return RetrofitClient.getInstance().getApiClient().
                postAddToFavorite(Constant.CONTENT_TYPE,Constant.TOKEN,refererKey,language,model);
    }
}
