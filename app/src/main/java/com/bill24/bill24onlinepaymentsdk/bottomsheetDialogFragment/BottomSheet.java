package com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.fragment.PaymentMethodFragment;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.conts.LanguageCode;
import com.bill24.bill24onlinepaymentsdk.model.core.RequestAPI;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheet extends BottomSheetDialogFragment {
    private String transactionId,refererKey,language;
    private CheckoutDetailRequestModel requestModel;
    private FrameLayout progressBarContainer;
    public BottomSheet(String transactionId,String refererKey,String language){
        this.transactionId=transactionId;
        this.refererKey=refererKey;
        this.language=language;
        requestModel=new CheckoutDetailRequestModel(this.transactionId);
    }
    public BottomSheet(String transactionId,String refererKey){
        this.transactionId=transactionId;
        this.refererKey=refererKey;
        requestModel=new CheckoutDetailRequestModel(transactionId);
    }

    private void initView(View view){
        progressBarContainer=view.findViewById(R.id.progress_circular);
    }
    public void showFragment(Fragment fragment){
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout,fragment)
                .commit();
    }

    private void showProgressIndicator(){
        int screenHeight=getResources().getDisplayMetrics().heightPixels;
        int newHeight=(int) (screenHeight*0.9);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,newHeight);
        progressBarContainer.setLayoutParams(layoutParams);

        progressBarContainer.setVisibility(View.VISIBLE);
    }
    private void hideProgressIndicator(){
        progressBarContainer.setVisibility(View.GONE);
    }

    private void postCheckoutDetail(){
        RequestAPI requestAPI=new RequestAPI(refererKey);
        Call<BaseResponse<CheckoutDetailModel>> call=requestAPI.postCheckoutDetail(requestModel);
        call.enqueue(new Callback<BaseResponse<CheckoutDetailModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckoutDetailModel>> call, Response<BaseResponse<CheckoutDetailModel>> response) {
                if(response.isSuccessful()){
                    List<BankPaymentMethodModel> bankPaymentMethodModelList=
                                (   response.body() !=null &&
                                    response.body().getData() !=null &&
                                    response.body().getData().getTransInfo() !=null &&
                                        response.body().getData().getTransInfo().getBankPaymentMethod() !=null
                                    ) ?

                            response.body().getData().getTransInfo().getBankPaymentMethod() : new ArrayList<>();
                    TransactionInfoModel transactionInfoModel=
                                         (
                                            response.body() !=null &&
                                            response.body().getData() !=null &&
                                            response.body().getData().getTransInfo()!=null
                                            ) ?
                            response.body().getData().getTransInfo() : new TransactionInfoModel();

                    if(!bankPaymentMethodModelList.isEmpty()){
                        setSharePreference(bankPaymentMethodModelList,transactionInfoModel);//Store value in sharePreference
                        showFragment(new PaymentMethodFragment());//Go to Fragment PaymentMethod
                    }

                    new Handler().postDelayed(() -> {
                        hideProgressIndicator();//Hide Progress Indicator
                    },100);
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<CheckoutDetailModel>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSharePreference(
            List<BankPaymentMethodModel> bankPaymentMethodModelList,
            TransactionInfoModel transactionInfoModel){
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Constant.KEY_PAYMENT_METHOD, SharePreferenceCustom.convertListObjectToJson(bankPaymentMethodModelList));
        editor.putString(Constant.KEY_LANGUAGE_CODE,language);
        editor.putString(Constant.KEY_REFERER_KEY,refererKey);
        editor.putString(Constant.KEY_TRANSACTION_INFO,SharePreferenceCustom.convertObjectToJson(transactionInfoModel));
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(language==null || language.isEmpty()){
            language= LanguageCode.KH;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog dialog= (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d= (BottomSheetDialog) dialogInterface;
            int blackColorWith65PercentAlpha = Color.argb(Color.alpha(R.color.barrier_color),0,0,0 );

            //Set Barrier Color
            d.getWindow().setBackgroundDrawable(new ColorDrawable(blackColorWith65PercentAlpha));

//
        // Change BottomSheet Background Transparent
//           FrameLayout bottomSheet=d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//           int transparentColor = Color.argb(0, 0, 0, 0);
//           bottomSheet.setBackgroundColor(transparentColor);

            //Set Height to BottomSheet
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = d.getBehavior();
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            //Set Max Height
            bottomSheetBehavior.setMaxHeight((int)(screenHeight*0.9));
            //Set Height When Dialog Load
            bottomSheetBehavior.setPeekHeight((int) (screenHeight*1)); // Set the initial peek height
            bottomSheetBehavior.setHideable(true);

        });

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        initView(view);
        showProgressIndicator();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postCheckoutDetail();
    }
}
