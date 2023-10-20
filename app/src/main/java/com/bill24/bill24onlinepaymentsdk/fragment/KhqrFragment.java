package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.core.RequestAPI;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhqrFragment extends Fragment {

    private AppCompatTextView textCustomerName,
            textAmount,textCurrency,textCountDownTime,
            textScanToPay,textDownload,textShare,textOr;
    private AppCompatImageView khqrImage,khqrCurrencyIcon;
    private FrameLayout downloadContainer,shareContainer,khqrLoading;
    private TransactionInfoModel transactionInfoModel;

    private String transactionId,refererKey,language;
    public KhqrFragment(String transactionId){
        this.transactionId=transactionId;
    }

    private void initView(View view){
        textCustomerName=view.findViewById(R.id.text_khqr_customer_name);
        textAmount=view.findViewById(R.id.text_khqr_amount);
        textCurrency=view.findViewById(R.id.text_khqr_currency);
        textCountDownTime=view.findViewById(R.id.text_timer);
        khqrImage=view.findViewById(R.id.khqr_image);
        khqrCurrencyIcon=view.findViewById(R.id.khqr_currency_icon);
        textScanToPay=view.findViewById(R.id.text_scan_to_pay);
        textDownload=view.findViewById(R.id.text_download);
        textShare=view.findViewById(R.id.text_share);
        textOr=view.findViewById(R.id.text_or);
        downloadContainer=view.findViewById(R.id.download_container);
        shareContainer=view.findViewById(R.id.share_container);
        khqrLoading=view.findViewById(R.id.khqr_loading);
    }

    private void updateFont(){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(getContext(),"km");
        textScanToPay.setTypeface(typeface);
        textScanToPay.setTextSize(13);
        textShare.setTypeface(typeface);
        textShare.setTextSize(12);
        textDownload.setTypeface(typeface);
        textDownload.setTextSize(12);
        textOr.setTypeface(typeface);
        textOr.setTextSize(11);
    }

    private void bindData(){
        textCustomerName.setText(transactionInfoModel.getCustomerName());
        textAmount.setText(transactionInfoModel.getTotalAmountDisplay());
        textCurrency.setText(transactionInfoModel.getCurrency());
        Picasso.get().load(transactionInfoModel.getKhqrImage()).into(khqrImage);
        if(transactionInfoModel.getCurrency()=="USD"){
            khqrCurrencyIcon.setImageResource(R.drawable.usd_khqr_logo);
        }else if(transactionInfoModel.getCurrency()=="KHR"){
            khqrCurrencyIcon.setImageResource(R.drawable.khr_khqr_logo);
        }

    }

    private void postExtendExpiredTime(){
        khqrLoading.setVisibility(View.VISIBLE);
        RequestAPI requestAPI=new RequestAPI(refererKey,language);
        ExpiredRequestModel requestModel=new ExpiredRequestModel(transactionId);
        Call<BaseResponse<ExpiredTransactionModel>> call =requestAPI.postExpireTran(requestModel);

        call.enqueue(new Callback<BaseResponse<ExpiredTransactionModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<ExpiredTransactionModel>> call, Response<BaseResponse<ExpiredTransactionModel>> response) {

                response.body();
                if(response.isSuccessful()){
                    khqrLoading.setVisibility(View.GONE);
                    BaseResponse<ExpiredTransactionModel> expiredTran=response.body();
                   // String expiredTime=expiredTran.getData().getExpiredDate();
                    String expiredTime="20-10-2023 10:39:34";//todo handle time from api
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    try {
                        Date expiredDate=dateFormat.parse(expiredTime);
                        Date currentDate=new Date();
                        long timeDifferenceMillisecond=expiredDate.getTime() - currentDate.getTime();

                        countTime(timeDifferenceMillisecond);

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {

                    Toast.makeText(getContext(),"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<ExpiredTransactionModel>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangLanguage.setLanguage("kh",getContext());
        SharedPreferences preferences= getActivity().getPreferences(Context.MODE_PRIVATE);
        String transactionInfoJson=preferences.getString(Constant.KEY_TRANSACTION_INFO,"");
        transactionInfoModel= SharePreferenceCustom.converJsonToObject(transactionInfoJson, TransactionInfoModel.class);
        //get language
        language=preferences.getString(Constant.KEY_LANGUAGE_CODE,"");
        //get refererKey
        refererKey=preferences.getString(Constant.KEY_REFERER_KEY,"");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.khqr_fragment_layout,container,false);
        initView(view);
        bindData();
        //Update Font
        updateFont();

        postExtendExpiredTime();//get expired date from api

        //todo handle download
        downloadContainer.setOnClickListener(v->{
            Toast.makeText(getContext(),"Download",Toast.LENGTH_SHORT).show();
        });

        //todo handle share
        shareContainer.setOnClickListener(v->{
            Toast.makeText(getContext(),"Share",Toast.LENGTH_SHORT).show();
        });




        return view;
    }




    private void countTime(long timeMillisecond){
        CountDownTimer countDownTimer=new CountDownTimer(timeMillisecond,1000){
            @Override
            public void onTick(long l) {
                long minute=l/1000/60;
                long second=(l/1000)%60;
                String timeLeftFormat=String.format(Locale.getDefault(),"%02d:%02d",minute,second);
                textCountDownTime.setText(timeLeftFormat);
            }

            @Override
            public void onFinish() {
                Fragment fragment=getParentFragment();
                if(fragment !=null && fragment instanceof BottomSheet){
                    ((BottomSheet)getParentFragment()).showFragment(new ExpireFragment(transactionId));
                }
            }
        };
        countDownTimer.start();
    }

}
