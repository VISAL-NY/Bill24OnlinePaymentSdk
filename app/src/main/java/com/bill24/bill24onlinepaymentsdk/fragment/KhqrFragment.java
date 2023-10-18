package com.bill24.bill24onlinepaymentsdk.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class KhqrFragment extends Fragment {

    private String languageCode;
    private AppCompatTextView textCustomerName,textAmount,textCurrency,textCountDownTime;
    private AppCompatImageView khqrImage,khqrCurrencyIcon;
    private TransactionInfoModel transactionInfoModel;


    public KhqrFragment(String languageCode){
        this.languageCode=languageCode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangLanguage.setLanguage(languageCode,getContext());

        Bundle bundle=getArguments();
        if(bundle != null){
            transactionInfoModel= (TransactionInfoModel) bundle.getSerializable("tranInfo");
            if(transactionInfoModel != null){

            }
            Log.e("khqrfragement", "onCreate: "+transactionInfoModel.getCustomerName(), null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.khqr_fragment_layout,container,false);
        initView(view);
        bindData();


       CountDownTimer countDownTimer=new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                long minute=l/1000/60;
                long second=(l/1000)%60;
                String timeLeftFormat=String.format(Locale.getDefault(),"%02d:%02d",minute,second);

                textCountDownTime.setText(timeLeftFormat);
            }

            @Override
            public void onFinish() {
                ((BottomSheet)getParentFragment()).showFragment(new ExpireFragment("km"));
            }
        };
        countDownTimer.start();
        return view;
    }

    private void initView(View view){
        textCustomerName=view.findViewById(R.id.text_khqr_customer_name);
        textAmount=view.findViewById(R.id.text_khqr_amount);
        textCurrency=view.findViewById(R.id.text_khqr_currency);
        textCountDownTime=view.findViewById(R.id.text_timer);
        khqrImage=view.findViewById(R.id.khqr_image);
        khqrCurrencyIcon=view.findViewById(R.id.khqr_currency_icon);
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


}
