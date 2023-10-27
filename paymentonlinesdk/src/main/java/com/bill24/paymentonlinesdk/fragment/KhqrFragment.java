package com.bill24.paymentonlinesdk.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.paymentonlinesdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.paymentonlinesdk.helper.ChangLanguage;
import com.bill24.paymentonlinesdk.helper.SetFont;
import com.bill24.paymentonlinesdk.helper.SharePreferenceCustom;
import com.bill24.paymentonlinesdk.model.ExpiredTransactionModel;
import com.bill24.paymentonlinesdk.model.TransactionInfoModel;
import com.bill24.paymentonlinesdk.model.baseResponseModel.BaseResponse;
import com.bill24.paymentonlinesdk.model.conts.Constant;
import com.bill24.paymentonlinesdk.model.conts.CurrencyCode;
import com.bill24.paymentonlinesdk.model.core.RequestAPI;
import com.bill24.paymentonlinesdk.model.requestModel.ExpiredRequestModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        Typeface typeface=font.setFont(getContext(),language);
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
        //load image
        Picasso.get().load(transactionInfoModel.getKhqrImage()).into(khqrImage);
        if(transactionInfoModel.getCurrency().equals(CurrencyCode.USD)){
            khqrCurrencyIcon.setImageResource(R.drawable.usd_khqr_logo);
        }else if(transactionInfoModel.getCurrency().equals(CurrencyCode.KHR)){
            khqrCurrencyIcon.setImageResource(R.drawable.khr_khqr_logo);
        }

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

    private void getSharePreference(){
        SharedPreferences preferences= getActivity().getPreferences(Context.MODE_PRIVATE);
        if(preferences!=null){
            String transactionInfoJson=preferences.getString(Constant.KEY_TRANSACTION_INFO,"");
            transactionInfoModel= SharePreferenceCustom.converJsonToObject(transactionInfoJson, TransactionInfoModel.class);
            //get language
            language=preferences.getString(Constant.KEY_LANGUAGE_CODE,"");
            //get refererKey
            refererKey=preferences.getString(Constant.KEY_REFERER_KEY,"");
        }

    }

    private void postExtendExpiredTime(){
        khqrLoading.setVisibility(View.VISIBLE);
        RequestAPI requestAPI=new RequestAPI(refererKey);
        ExpiredRequestModel requestModel=new ExpiredRequestModel(transactionId);
        Call<BaseResponse<ExpiredTransactionModel>> call =requestAPI.postExpireTran(requestModel);

        call.enqueue(new Callback<BaseResponse<ExpiredTransactionModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<ExpiredTransactionModel>> call, Response<BaseResponse<ExpiredTransactionModel>> response) {

                if(response.isSuccessful()){
                    khqrLoading.setVisibility(View.GONE);
                    BaseResponse<ExpiredTransactionModel> expiredTran=response.body();
                   // String expiredTime=expiredTran.getData().getExpiredDate();
                    String expiredTime="24-10-2023 23:55:34";//todo handle time from api
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US);
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

    private Bitmap convertLayoutToImage(View view){
        AppCompatTextView customerName=view.findViewById(R.id.text_khqr_customer_name);
        AppCompatTextView amount=view.findViewById(R.id.text_khqr_amount);
        AppCompatImageView qrcodeImage=view.findViewById(R.id.khqr_image_download_share);
        AppCompatImageView khqrCurrencyLogo=view.findViewById(R.id.khqr_currency_icon_download_share);

        customerName.setText(transactionInfoModel.getCustomerName());
        amount.setText(transactionInfoModel.getTotalAmountDisplay());
        if(transactionInfoModel.getCurrency().equals(CurrencyCode.KHR)){
            khqrCurrencyLogo.setImageResource(R.drawable.khr_khqr_logo);
        }
        else if(transactionInfoModel.getCurrency().equals(CurrencyCode.USD)){
            khqrCurrencyLogo.setImageResource(R.drawable.usd_khqr_logo);
        }
        //load image from internet
       Picasso.get().load(transactionInfoModel.getKhqrImage()).into(qrcodeImage);

        view.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
        return  Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(),Bitmap.Config.ARGB_8888);
    }


    private void  customSnackBar(View view,int image,String desc){
        Snackbar customSnackbar = Snackbar.make(view.findViewById(R.id.container_khqrfragment), "",Snackbar.LENGTH_SHORT);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) customSnackbar.getView();
        View customView = getLayoutInflater().inflate(R.layout.snackbar_success_custom_layout, null);
        snackbarLayout.setBackgroundColor(Color.TRANSPARENT);//remove snackbar background
        snackbarLayout.addView(customView, 0);

        // Customize the content and appearance of the custom layout
            AppCompatTextView textView = customView.findViewById(R.id.custom_snackbar_desc);
            textView.setText(desc);

            AppCompatImageView imageView=customView.findViewById(R.id.custom_snackbar_icon);
            imageView.setImageResource(image);

        customSnackbar.show();
    }

    private void downloadKHQR(Bitmap bitmap,View view){
        Date now = new Date();
        long currentTimeInMilliseconds = System.currentTimeMillis();
        int microseconds = (int) ((currentTimeInMilliseconds % 1000) * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        String formattedDateTime = dateFormat.format(now);

        String imageTitle="KHQR Image "+formattedDateTime+microseconds;
        String imageUrl= MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(),bitmap,imageTitle,"");

        if(imageUrl!=null){
            customSnackBar(view,R.drawable.check_24,"Image saved !");
        }else {
            customSnackBar(view,R.drawable.close_24,"Failed to save image !");
        }
    }
    private void shareKHQR(Bitmap bitmap){
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp_image", ".jpg", getContext().getCacheDir());
            FileOutputStream fos = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            Uri uri = FileProvider.getUriForFile(getContext(), Constant.AUTHORITY, tempFile);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharePreference();
        ChangLanguage.setLanguage(language,getContext());

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

        View layoutImage=getLayoutInflater().inflate(R.layout.download_share_card_khqr_image_layout,null);

        downloadContainer.setOnClickListener(v->{
             Bitmap bitmap=convertLayoutToImage(layoutImage);
             Canvas canvas=new Canvas(bitmap);
             layoutImage.draw(canvas);

             //Save Image into Gallerry
             downloadKHQR(bitmap,view);

        });

        shareContainer.setOnClickListener(v->{
            Bitmap bitmap=convertLayoutToImage(layoutImage);
            Canvas canvas=new Canvas(bitmap);
            layoutImage.draw(canvas);

            shareKHQR(bitmap);

        });

        return view;
    }
}
