package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.adapter.PaymentMethodAdapter;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.ConnectivityState;
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
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodFragment extends Fragment
        implements PaymentMethodAdapter.PaymentMethodClickListener,
        ConnectivityState.ConnectivityListener {
    private ConnectivityState connectivityState;
    private RecyclerView recyclerViewPaymentMethod;
    private AppCompatTextView textVersion,
            textTotalAmount,textCurrency,textPaymentMethod,
            textTotalAmountTitle;
    private LinearLayoutCompat containerPaymentMethod;
    private List<BankPaymentMethodModel> bankPaymentMethodModelList;
    private TransactionInfoModel transactionInfoModel;
    private String refererKey,language;
    public PaymentMethodFragment(){
    }


    private void initView(View view){
        textPaymentMethod=view.findViewById(R.id.text_payment_method);
        recyclerViewPaymentMethod=view.findViewById(R.id.recycler_payment_method);
        textTotalAmountTitle=view.findViewById(R.id.text_total_amount_title);
        textVersion=view.findViewById(R.id.text_version);
        textCurrency=view.findViewById(R.id.text_total_amount_currency);
        textTotalAmount =view.findViewById(R.id.text_total_amount);
        containerPaymentMethod=view.findViewById(R.id.container_payment_method);
    }

    private void bindView(){
        textTotalAmount.setText(transactionInfoModel.getTranAmountDisplay());
        textCurrency.setText(transactionInfoModel.getCurrency());
    }

    private void updateFont(){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(getContext(),language);

        textPaymentMethod.setTypeface(typeface);
        textPaymentMethod.setTextSize(16);
        textPaymentMethod.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textPaymentMethod.setTextColor(getResources().getColor(R.color.header_font_color));

        textTotalAmountTitle.setTypeface(typeface);
        textTotalAmountTitle.setTextSize(10);

    }

    private void setupRecyclerView(PaymentMethodAdapter.PaymentMethodClickListener listener){
//        List<BankPaymentMethodItemModel> bankPaymentMethodItemModels=new ArrayList<>();
//        bankPaymentMethodItemModels.add(new BankPaymentMethodItemModel("MASTERCARD","Credit or Debit Card"));
//
//        List<BankPaymentMethodItemModel> bankPaymentMethodItemModels1=new ArrayList<>();
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//        bankPaymentMethodItemModels1.add(new BankPaymentMethodItemModel("AMK","AMK"));
//
//
//
//        List<BankPaymentMethodModel> bankPaymentMethodModelList1=new ArrayList<>();
//        bankPaymentMethodModelList1.add(new BankPaymentMethodModel("KHQR or Credit/Debit Card",bankPaymentMethodItemModels));
//        bankPaymentMethodModelList1.add(new BankPaymentMethodModel("Bank Payment",bankPaymentMethodItemModels1));



        if(bankPaymentMethodModelList !=null && !bankPaymentMethodModelList.isEmpty()){
            PaymentMethodAdapter adapter=new PaymentMethodAdapter();
            adapter.setPaymentMethod(transactionInfoModel,bankPaymentMethodModelList,transactionInfoModel.getTranId(),refererKey,language);
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
    private void postExpiredTran(ExpiredRequestModel model){
        RequestAPI requestAPI=new RequestAPI(refererKey);
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

    private void getPreference(){
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        String bankPaymentMethodJson=preferences.getString(Constant.KEY_PAYMENT_METHOD,"");
        bankPaymentMethodModelList=SharePreferenceCustom.convertFromJsonToListObject(bankPaymentMethodJson,BankPaymentMethodModel.class);
        String transactionInfoJson=preferences.getString(Constant.KEY_TRANSACTION_INFO,"");
        transactionInfoModel=SharePreferenceCustom.converJsonToObject(transactionInfoJson, TransactionInfoModel.class);

        //get language
        language=preferences.getString(Constant.KEY_LANGUAGE_CODE,"");
        //get refererKey
        refererKey=preferences.getString(Constant.KEY_REFERER_KEY,"");
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityState=new ConnectivityState(this);//Init BroadCastReceiver

        getPreference();
        ChangLanguage.setLanguage(language,getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.payment_method_fragment_layout,container,false);
        initView(view);

        //set container of webview height to 90%
        int screenHeight=getResources().getDisplayMetrics().heightPixels;
        int newHeight=(int) (screenHeight*0.9);
        LinearLayoutCompat.LayoutParams layoutParams=new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,newHeight);
        containerPaymentMethod.setLayoutParams(layoutParams);


        //Update Font
        updateFont();

        bindView();

        //Set Up RecyclerView
        setupRecyclerView(this);

        displayOsVersion();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister broadcast when fragment is pause
        requireContext().unregisterReceiver(connectivityState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //register broadcast when connectivity change
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireContext().registerReceiver(connectivityState,intentFilter);
    }

    @Override
    public void OnItemPaymentMethodClick(BankPaymentMethodItemModel itemModel) {

        ExpiredRequestModel expiredRequestModel=new ExpiredRequestModel(transactionInfoModel.getTranId());
        switch (itemModel.getId()){
            case Bank.KHQR:
                postExpiredTran(expiredRequestModel);
                Fragment fragment=getParentFragment();
                if(fragment !=null && fragment instanceof BottomSheet){
                    ((BottomSheet)getParentFragment()).showFragment(new KhqrFragment(transactionInfoModel.getTranId()));
                }
                break;
            default:
                postExpiredTran(expiredRequestModel);
                GenerateDeeplinkRequestModel generateDeeplinkRequestModel=new GenerateDeeplinkRequestModel(itemModel.getId(),"2DCDFE54ED2F");

                RequestAPI requestAPI=new RequestAPI(refererKey);
                Call<BaseResponse<GenerateLinkDeepLinkModel>> call=requestAPI.postGenerateDeeplink(generateDeeplinkRequestModel);
                call.enqueue(new Callback<BaseResponse<GenerateLinkDeepLinkModel>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<GenerateLinkDeepLinkModel>> call, Response<BaseResponse<GenerateLinkDeepLinkModel>> response) {
                        GenerateLinkDeepLinkModel generateLinkDeepLinkModel;
                        if(response.isSuccessful()){
                            BaseResponse<GenerateLinkDeepLinkModel> deeplink=response.body();
                            if(deeplink !=null){
                                generateLinkDeepLinkModel=deeplink.getData();
                                if(itemModel.isSupportDeeplink()){
                                    launchDeeplink(generateLinkDeepLinkModel.getMobileDeepLink());
                                }
                                ((BottomSheet)getParentFragment()).showFragment(new WebViewCheckoutFragment(generateLinkDeepLinkModel.getWebPaymentLink()));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse<GenerateLinkDeepLinkModel>> call, Throwable t) {
                        Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected, boolean wasResortConnection) {
        if(isConnected){
            if(!wasResortConnection){
                //todo handle display connection restore
                //Toast.makeText(getContext(), "have internet", Toast.LENGTH_SHORT).show();
            }
        }else {
            //todo handle display lose connection
            //Toast.makeText(getContext(), "no internet", Toast.LENGTH_SHORT).show();

        }
    }
}


