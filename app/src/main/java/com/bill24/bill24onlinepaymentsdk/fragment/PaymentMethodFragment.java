package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.adapter.PaymentMethodAdapter;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.helper.StickyHeaderItemDecoration;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodItemModel;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.bill24.bill24onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.bill24onlinepaymentsdk.model.GenerateLinkDeepLinkModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.conts.Bank;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.core.RequestAPI;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.GenerateDeeplinkRequestModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodFragment extends Fragment implements PaymentMethodAdapter.PaymentMethodClickListener {
    private RecyclerView recyclerViewPaymentMethod;
    private AppCompatTextView textVersion,
            textTotalAmount,textCurrency,textPaymentMethod,
            textTotalAmountTitle;
    private List<BankPaymentMethodModel> bankPaymentMethodModelList;
    private TransactionInfoModel transactionInfoModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangLanguage.setLanguage("kh",getContext());
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        String bankPaymentMethodJson=preferences.getString(Constant.KEY_PAYMENT_METHOD,"");
        bankPaymentMethodModelList=SharePreferenceCustom.convertFromJsonToListObject(bankPaymentMethodJson,BankPaymentMethodModel.class);
        String transactionInfoJson=preferences.getString(Constant.KEY_TRANSACTION_INFO,"");
        transactionInfoModel=SharePreferenceCustom.converJsonToObject(transactionInfoJson, TransactionInfoModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.payment_method_fragment_layout,container,false);
        initView(view);
        bindView();

        //Set Up RecyclerView
        setupRecyclerView(this);

        //Update Font
        updateFont();


        //Change Font Family and Font Size
    //    Typeface typeface;
//        if(languageCode== "en"){
//            typeface= ResourcesCompat.getFont(getContext(),R.font.roboto_bold);
//        }else {
//            typeface=ResourcesCompat.getFont(getContext(),R.font.battambang_bold);
//        }
//        textPaymentMethod.setTypeface(typeface);
//        textPaymentMethod.setTextSize(16);
//        textTotalAmountTitle.setTypeface(typeface);
//        textTotalAmountTitle.setTextSize(10);

        //Display Version to Screen
        displayOsVersion();

        return view;
    }

    private void initView(View view){
        textPaymentMethod=view.findViewById(R.id.text_payment_method);
        recyclerViewPaymentMethod=view.findViewById(R.id.recycler_payment_method);
        textTotalAmountTitle=view.findViewById(R.id.text_total_amount_title);
        textVersion=view.findViewById(R.id.text_version);
        textCurrency=view.findViewById(R.id.text_total_amount_currency);
        textTotalAmount =view.findViewById(R.id.text_total_amount);
    }

    private void bindView(){
        textTotalAmount.setText(transactionInfoModel.getTranAmountDisplay());
        textCurrency.setText(transactionInfoModel.getCurrency());
    }

    private void updateFont(){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(getContext(),"km");
        textPaymentMethod.setTypeface(typeface);
        textPaymentMethod.setTextSize(16);
        textPaymentMethod.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textPaymentMethod.setTextColor(getResources().getColor(R.color.header_font_color));

        textTotalAmountTitle.setTypeface(typeface);
        textTotalAmountTitle.setTextSize(10);


    }

    private void setupRecyclerView(PaymentMethodAdapter.PaymentMethodClickListener listener){
        if(bankPaymentMethodModelList !=null && !bankPaymentMethodModelList.isEmpty()){
            PaymentMethodAdapter adapter=new PaymentMethodAdapter();
            adapter.setPaymentMethod(bankPaymentMethodModelList);
            adapter.setOnItemClickListener(listener);
            recyclerViewPaymentMethod.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewPaymentMethod.setAdapter(adapter);
             //Set Header to Sticky
            StickyHeaderItemDecoration stickyHeaderItemDecoration=new StickyHeaderItemDecoration((StickyHeaderItemDecoration.StickyHeaderInterface)adapter);
            recyclerViewPaymentMethod.addItemDecoration(stickyHeaderItemDecoration);
        }
    }

    private void displayOsVersion(){
        try {
            PackageInfo packageInfo=getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
            String version=packageInfo.versionName;
            int versionCode=packageInfo.versionCode;
            textVersion.setText("V"+version+"."+versionCode);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void OnItemPaymentMethodClick(BankPaymentMethodItemModel itemModel) {
        //Toast.makeText(getContext(),"new click"+itemModel.getId(),Toast.LENGTH_SHORT).show();

        ExpiredRequestModel expiredRequestModel=new ExpiredRequestModel(transactionInfoModel.getTranId());
        GenerateDeeplinkRequestModel generateDeeplinkRequestModel=new GenerateDeeplinkRequestModel(itemModel.getId(),transactionInfoModel.getTranId());
        switch (itemModel.getId()){
            case Bank.KHQR:
                Fragment fragment=getParentFragment();
                if(fragment !=null && fragment instanceof BottomSheet){
                    ((BottomSheet)getParentFragment()).showFragment(new KhqrFragment());
                }
                break;
            case Bank.AMK:
                postExpiredTran(expiredRequestModel);
                postGenerateDeeplink(generateDeeplinkRequestModel);


                //Todo set deeplink and web checkout
                if(itemModel.isSupportDeeplink()){

                }
                if(itemModel.isSupportCheckoutPage()){
                    ((BottomSheet)getParentFragment()).showFragment(new WebViewCheckoutFragment());
                }
                break;
        }

    }

    @Override
    public void OnFavoriteIconClick() {

    }

    private void postExpiredTran(ExpiredRequestModel model){
        RequestAPI requestAPI=new RequestAPI();
        Call<BaseResponse<ExpiredTransactionModel>> call=requestAPI.postExpireTran(model);
        call.enqueue(new Callback<BaseResponse<ExpiredTransactionModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<ExpiredTransactionModel>> call, Response<BaseResponse<ExpiredTransactionModel>> response) {

            }
            @Override
            public void onFailure(Call<BaseResponse<ExpiredTransactionModel>> call, Throwable t) {

            }
        });
    }


    //todo launch deeplink mobile
   private void launchDeeplink(String url){
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(intent);
   }


   //todo request deeplink
   private void postGenerateDeeplink(GenerateDeeplinkRequestModel model){
        RequestAPI requestAPI=new RequestAPI();
        Call<BaseResponse<GenerateLinkDeepLinkModel>> call=requestAPI.postGenerateDeeplink(model);
        call.enqueue(new Callback<BaseResponse<GenerateLinkDeepLinkModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<GenerateLinkDeepLinkModel>> call, Response<BaseResponse<GenerateLinkDeepLinkModel>> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<BaseResponse<GenerateLinkDeepLinkModel>> call, Throwable t) {

            }
        });
   }

}


