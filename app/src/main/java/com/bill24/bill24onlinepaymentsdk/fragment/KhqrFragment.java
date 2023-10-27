package com.bill24.bill24onlinepaymentsdk.fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.CustomShape;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.SelectedState;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.ConvertColorHexa;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutPageConfigModel;
import com.bill24.bill24onlinepaymentsdk.model.ExpiredTransactionModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.darkMode.DarkModeModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.lightMode.LightModeModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.conts.CurrencyCode;
import com.bill24.bill24onlinepaymentsdk.core.RequestAPI;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.ExpiredRequestModel;
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
    private AppCompatImageView khqrImage,khqrCurrencyIcon,imageDownload,imageShare;
    private FrameLayout downloadContainer,shareContainer,khqrLoading,
            khqrCardContainer,containerQrCode;
    private RelativeLayout containerLoadingTime;
    private TransactionInfoModel transactionInfoModel;
    private CheckoutPageConfigModel checkoutPageConfigModel;
    private AppCompatImageView dashLineLeft,dashLineRight;
    private String transactionId,refererKey,language;
    private CoordinatorLayout khqrContainer;
    private View khqrBackground;
    private  boolean isLightMode;
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
        containerLoadingTime=view.findViewById(R.id.container_loading_time);
        khqrContainer=view.findViewById(R.id.container_khqrfragment);
        imageDownload=view.findViewById(R.id.image_download);
        imageShare=view.findViewById(R.id.image_share);
        dashLineLeft=view.findViewById(R.id.or_dash_line_left);
        dashLineRight=view.findViewById(R.id.or_dash_line_right);
        khqrCardContainer=view.findViewById(R.id.khqr_container);
        khqrBackground=view.findViewById(R.id.khqr_background_color);
        containerQrCode=view.findViewById(R.id.container_qr_code);

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

            //checkout page config
            String checkoutPageConfigJson=preferences.getString(Constant.KEY_CHECKOUT_PAGE_CONFIG,"");
            checkoutPageConfigModel=SharePreferenceCustom.converJsonToObject(checkoutPageConfigJson, CheckoutPageConfigModel.class);

            //get isLight mode
            isLightMode=preferences.getBoolean(Constant.IS_LIGHT_MODE,true);

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
                    String expiredTime=expiredTran.getData().getExpiredDate();
                    //String expiredTime="2023-10-26 15:54:20";
                    try {
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date expiredDate=dateFormat.parse(expiredTime);
                        Date currentDate=new Date();
                        long timeDifferenceMillisecond=expiredDate.getTime() - currentDate.getTime();

                        if(timeDifferenceMillisecond > 1800000){//30 minutes
                            containerLoadingTime.setVisibility(View.INVISIBLE);
                        }else {
                            containerLoadingTime.setVisibility(View.VISIBLE);
                        }

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

        //update font family
            SetFont font=new SetFont();
            Typeface typeface=font.setFont(getContext(),language);

        // Customize the content and appearance of the custom layout
            AppCompatTextView textView = customView.findViewById(R.id.custom_snackbar_desc);
            textView.setTypeface(typeface);
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
            customSnackBar(view,R.drawable.check_circle_24px,getResources().getString(R.string.image_saved));
        }else {
            customSnackBar(view,R.drawable.error_24px,getResources().getString(R.string.image_unsave));
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


    private void applyStyleShapeLightMode(){

        LightModeModel lightModeModel= checkoutPageConfigModel.getAppearance().getLightMode();


    //khqr container
       String bgKhqrContainerColor=lightModeModel.getSecondaryColor().getBackgroundColor();
       String bgKhqrContainerHexa=ConvertColorHexa.convertHex(bgKhqrContainerColor);
       khqrContainer.setBackgroundColor(Color.parseColor(bgKhqrContainerHexa));

       //text scan text or
        String scanPayColor=lightModeModel.getPrimaryColor().getTextColor();
        String scanPayColorHexa=ConvertColorHexa.convertHex(scanPayColor);
        textScanToPay.setTextColor(Color.parseColor(scanPayColorHexa));
        textOr.setTextColor(Color.parseColor(scanPayColorHexa));

        // dash line
        String dashLine=lightModeModel.getSecondaryColor().getTextColor();
        String dashLineHexa=ConvertColorHexa.convertHex(dashLine);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.LINE);
        gradientDrawable.setStroke(1,
                Color.parseColor(dashLineHexa),
                15.0f,
                15.0f); // Set the stroke color and width
        gradientDrawable.setCornerRadius(20); // Set the corner radius
        gradientDrawable.setDither(true);

        dashLineLeft.setBackground(gradientDrawable);
        dashLineRight.setBackground(gradientDrawable);


        //count time
        String countTimeColor=lightModeModel.getSecondaryColor().getTextColor();
        String countTimeColorHexa=ConvertColorHexa.convertHex(countTimeColor);
        textCountDownTime.setTextColor(Color.parseColor(countTimeColorHexa));




       //apply shape
       String bgDownloadShare=lightModeModel.getButton().getActionButton().getBackgroundColor();
       String bgDownloadShareHexa=ConvertColorHexa.convertHex(bgDownloadShare);

        ShapeDrawable shape=CustomShape.applyShape(Color.parseColor(bgDownloadShareHexa),20);
        String downloadShareSelectedColor=ConvertColorHexa.getFiftyPercentColor(bgDownloadShare);
        ShapeDrawable shape1=CustomShape.applyShape(Color.parseColor(downloadShareSelectedColor),20);


        StateListDrawable selectorDownload= SelectedState.selectedSate(shape,shape1);
        downloadContainer.setBackground(selectorDownload);

        StateListDrawable selectorShare=SelectedState.selectedSate(shape,shape1);


        shareContainer.setBackground(selectorShare);

       //apply icon color
        String iconColor=lightModeModel.getButton().getActionButton().getTextColor();
        String iconColorHexa=ConvertColorHexa.convertHex(iconColor);
        ColorFilter colorFilterFavicon=new PorterDuffColorFilter(Color.parseColor(iconColorHexa), PorterDuff.Mode.SRC_ATOP);
        imageShare.setColorFilter(colorFilterFavicon);
        imageDownload.setColorFilter(colorFilterFavicon);

        //download share
        String downloadShareColor=lightModeModel.getSecondaryColor().getTextColor();
        String downloadShareHexa=ConvertColorHexa.convertHex(downloadShareColor);

        textDownload.setTextColor(Color.parseColor(downloadShareHexa));
        textShare.setTextColor(Color.parseColor(downloadShareHexa));





    }


    private void applyStyleShapeDarkMode(){

        DarkModeModel darkModeModel= checkoutPageConfigModel.getAppearance().getDarkMode();


        //khqr container
        String bgKhqrContainerColor=darkModeModel.getSecondaryColor().getBackgroundColor();
        String bgKhqrContainerHexa=ConvertColorHexa.convertHex(bgKhqrContainerColor);
        khqrContainer.setBackgroundColor(Color.parseColor(bgKhqrContainerHexa));

        //text scan text or
        String scanPayColor=darkModeModel.getPrimaryColor().getTextColor();
        String scanPayColorHexa=ConvertColorHexa.convertHex(scanPayColor);
        textScanToPay.setTextColor(Color.parseColor(scanPayColorHexa));
        textOr.setTextColor(Color.parseColor(scanPayColorHexa));

        // dash line
        String dashLine=darkModeModel.getSecondaryColor().getTextColor();
        String dashLineHexa=ConvertColorHexa.convertHex(dashLine);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.LINE);
        gradientDrawable.setStroke(1,
                Color.parseColor(dashLineHexa),
                15.0f,
                15.0f); // Set the stroke color and width// Set the corner radius
        gradientDrawable.setDither(true);

        dashLineLeft.setBackground(gradientDrawable);
        dashLineRight.setBackground(gradientDrawable);


        //count time
        String countTimeColor=darkModeModel.getSecondaryColor().getTextColor();
        String countTimeColorHexa=ConvertColorHexa.convertHex(countTimeColor);
        textCountDownTime.setTextColor(Color.parseColor(countTimeColorHexa));




        //apply shape
        String bgDownloadShare=darkModeModel.getButton().getActionButton().getBackgroundColor();
        String bgDownloadShareHexa=ConvertColorHexa.convertHex(bgDownloadShare);


        ShapeDrawable shape=CustomShape.applyShape(Color.parseColor(bgDownloadShareHexa),20);
        String downloadShareSelectedColor=ConvertColorHexa.getFiftyPercentColor(bgDownloadShare);
        ShapeDrawable shape1=CustomShape.applyShape(Color.parseColor(downloadShareSelectedColor),20);


        StateListDrawable selectorDownload= SelectedState.selectedSate(shape,shape1);
        downloadContainer.setBackground(selectorDownload);

        StateListDrawable selectorShare=SelectedState.selectedSate(shape,shape1);


        shareContainer.setBackground(selectorShare);

        //apply icon color
        String iconColor=darkModeModel.getButton().getActionButton().getTextColor();
        String iconColorHexa=ConvertColorHexa.convertHex(iconColor);
        ColorFilter colorFilterFavicon=new PorterDuffColorFilter(Color.parseColor(iconColorHexa), PorterDuff.Mode.SRC_ATOP);
        imageShare.setColorFilter(colorFilterFavicon);
        imageDownload.setColorFilter(colorFilterFavicon);

        //download share
        String downloadShareColor=darkModeModel.getSecondaryColor().getTextColor();
        String downloadShareHexa=ConvertColorHexa.convertHex(downloadShareColor);

        textDownload.setTextColor(Color.parseColor(downloadShareHexa));
        textShare.setTextColor(Color.parseColor(downloadShareHexa));




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

        //applyStyle
        if(isLightMode){
            applyStyleShapeLightMode();

        }else {
            applyStyleShapeDarkMode();
        }

        //apply khqr card corner
        ShapeDrawable khqrCard=CustomShape.applyShape(Color.WHITE,100.0f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int shadowColor=Color.argb(90,0,0,0);
            khqrCardContainer.setOutlineSpotShadowColor(shadowColor);
        }
        khqrCardContainer.setElevation(20f);
        khqrCardContainer.setBackground(khqrCard);

        ShapeDrawable containerQrcode=CustomShape.applyShape(Color.WHITE,100.0f);
        containerQrCode.setBackground(containerQrcode);

        ShapeDrawable khqrBg=CustomShape.applyShape(
                getResources().getColor(R.color.khqr_backgound_color),100.0f);
        khqrBackground.setBackground(khqrBg);



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
