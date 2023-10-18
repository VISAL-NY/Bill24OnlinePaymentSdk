package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.adapter.PaymentMethodAdapter;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.StickyHeaderItemDecoration;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.core.RetrofitClient;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.resonseModel.CheckoutDetailResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodFragment extends Fragment implements PaymentMethodAdapter.PaymentMethodClickListener {
    private RecyclerView recyclerViewPaymentMethod;
    private AppCompatTextView textVersion,textPaymentMethod,textTotalAmount,textTotalAmountTitle;
    private FrameLayout progressBar;
    private List<BankPaymentMethodModel> bankPaymentMethodModelList;
    private String languageCode;
    private TransactionInfoModel transactionInfoModel;
    private PaymentMethodAdapter adapter;
    private CheckoutDetailRequestModel requestModel=new CheckoutDetailRequestModel();
    public PaymentMethodFragment(String languageCode){
        this.languageCode=languageCode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangLanguage.setLanguage(languageCode,getContext());
        requestModel.setTranId("1");
        transactionInfoModel=new TransactionInfoModel("My Name","USD","https://chart.apis.google.com/chart?cht=qr&chs=300x300&chld=L|0&chl=00020101021230350016amkbkhppxxx%40amkb010436550203AMK520449005802KH5913Virackbot+Jr.6010Phnom+Penh540510.20530384055020256040.2062380306Bill240504D90107031110802V21103CH163043B70","10.4");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.payment_method_fragment_layout,container,false);
        initView(view);
        bindView();
        progressBar.setVisibility(View.VISIBLE);
        //
        postCheckoutDetail();


        //Change Font Family and Font Size
        Typeface typeface;
        if(languageCode=="en"){
            typeface= ResourcesCompat.getFont(getContext(),R.font.roboto_bold);
        }else {
            typeface=ResourcesCompat.getFont(getContext(),R.font.battambang_bold);
        }
        textPaymentMethod.setTypeface(typeface);
        textPaymentMethod.setTextSize(16);
        textTotalAmountTitle.setTypeface(typeface);
        textTotalAmountTitle.setTextSize(10);

        //Display Version to Screen
        try {
            PackageInfo packageInfo=getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
            String version=packageInfo.versionName;
            int versionCode=packageInfo.versionCode;
            textVersion.setText("V"+version+"."+versionCode);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }


//        PaymentMethodAdapter  paymentMethodAdapter=new PaymentMethodAdapter();
//        paymentMethodAdapter.setPaymentMethod(bankPaymentMethodModelList);
      //  paymentMethodAdapter.setOnItemClickListener(this);
//        recyclerViewPaymentMethod.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewPaymentMethod.setAdapter(paymentMethodAdapter);

        return view;
    }

   // @Override
   // public void OnItemPaymentMethodClick(int position) {
//        int myPosition= itemList.get(position).hashCode();
//       Toast.makeText(getContext(),""+myPosition,Toast.LENGTH_SHORT).show();


//Replace Fragment
//        KhqrFragment khqrFragment=new KhqrFragment("km");
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("tranInfo", (Serializable) transactionInfoModel);
//        khqrFragment.setArguments(bundle);
//
//        Log.e("transactionddd", "OnItemPaymentMethodClick: "+transactionInfoModel.getTranId(),null );
//
//        ((BottomSheet)getParentFragment()).showFragment(khqrFragment);

    //}

//    @Override
//    public void OnFavoriteIconClick(int position) {
//        //Toast.makeText(getContext(),itemList.get(position),Toast.LENGTH_SHORT).show();
//
//    }

    private void initView(View view){
        recyclerViewPaymentMethod=view.findViewById(R.id.recycler_payment_method);
        textVersion=view.findViewById(R.id.text_version);
        textPaymentMethod=view.findViewById(R.id.text_payment_method);
        textTotalAmountTitle=view.findViewById(R.id.text_total_amount_title);
        textTotalAmount =view.findViewById(R.id.text_total_amount);
        progressBar=view.findViewById(R.id.progress_circular);
    }

    private void bindView(){
        textTotalAmount.setText(transactionInfoModel.getTotalAmountDisplay());
        textTotalAmount.setTextSize(21);
    }


    private void setupRecyclerView(){
        if(bankPaymentMethodModelList !=null && !bankPaymentMethodModelList.isEmpty()){
            adapter=new PaymentMethodAdapter();
            adapter.setPaymentMethod(bankPaymentMethodModelList);
            adapter.setOnItemClickListener(new PaymentMethodAdapter.PaymentMethodClickListener() {
                @Override
                public void OnItemPaymentMethodClick(String id) {
                    Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFavoriteIconClick(boolean isFavorite) {
                    Toast.makeText(getContext(),""+isFavorite,Toast.LENGTH_SHORT).show();

                }
            });
            recyclerViewPaymentMethod.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewPaymentMethod.setAdapter(adapter);
        //Set Header to Sticky
            StickyHeaderItemDecoration stickyHeaderItemDecoration=new StickyHeaderItemDecoration((StickyHeaderItemDecoration.StickyHeaderInterface)adapter);
            recyclerViewPaymentMethod.addItemDecoration(stickyHeaderItemDecoration);


        }
    }

    private void postCheckoutDetail(){
        Call<CheckoutDetailResponseModel> call=
                RetrofitClient.getInstance().getApiClient()
                                .postCheckoutDetail(Constant.TOKEN,requestModel);

        call.enqueue(new Callback<CheckoutDetailResponseModel>() {
            @Override
            public void onResponse(Call<CheckoutDetailResponseModel> call, Response<CheckoutDetailResponseModel> response) {
                displayProgressIndicator();
                if(response.isSuccessful()){
                    bankPaymentMethodModelList=response.body().getData().getTransInfo().getBankPaymentMethod();
                    hideProgressIndicator();
                    setupRecyclerView();
                }
               // transactionInfoModel=response.body().getData().getTransInfo();
            }
            @Override
            public void onFailure(Call<CheckoutDetailResponseModel> call, Throwable t) {
                Log.e("CBAAAA", "onFailure: "+t.getMessage(), null);

            }
        });
    }


    @Override
    public void OnItemPaymentMethodClick(String id) {
            //Toast.makeText(getContext(),""+bankPaymentMethodModelList.get(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFavoriteIconClick(boolean isFavorite) {

    }


    private void displayProgressIndicator(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressIndicator(){
        progressBar.setVisibility(View.GONE);
    }
}


