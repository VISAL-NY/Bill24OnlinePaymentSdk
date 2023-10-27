package com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;


import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.fragment.PaymentMethodFragment;
import com.bill24.bill24onlinepaymentsdk.helper.ConvertColorHexa;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.model.BankPaymentMethodModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutDetailModel;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutPageConfigModel;
import com.bill24.bill24onlinepaymentsdk.model.TransactionInfoModel;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;
import com.bill24.bill24onlinepaymentsdk.model.conts.LanguageCode;
import com.bill24.bill24onlinepaymentsdk.core.RequestAPI;
import com.bill24.bill24onlinepaymentsdk.model.requestModel.CheckoutDetailRequestModel;
import com.bill24.bill24onlinepaymentsdk.model.baseResponseModel.BaseResponse;
import com.bill24.bill24onlinepaymentsdk.socketIO.EVentName;
import com.bill24.bill24onlinepaymentsdk.socketIO.SocketManager;
import com.bill24.bill24onlinepaymentsdk.socketIO.model.SocketRespModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheet extends BottomSheetDialogFragment {
    private String transactionId,refererKey,language;
    private CheckoutDetailRequestModel requestModel;
    private FrameLayout progressBarContainer;
    private LinearLayoutCompat bottomSheet;
    private View dragHandle;
    private boolean isLightMode;
    private BottomSheetDialog dialog;
    private Class<? extends Activity> activityClass;
    public BottomSheet(
            String transactionId,
            String refererKey,
            boolean isLightMode,
            Class<? extends Activity> activityClass,
            String language){
        this.transactionId=transactionId;
        this.refererKey=refererKey;
        this.isLightMode=isLightMode;
        this.activityClass=activityClass;
        this.language=language;
        requestModel=new CheckoutDetailRequestModel(this.transactionId);
    }
    public BottomSheet(
            String transactionId,
            String refererKey,
            boolean isLightMode,
            Class<? extends Activity> activityClass){
        this.transactionId=transactionId;
        this.refererKey=refererKey;
        this.isLightMode=isLightMode;
        this.activityClass=activityClass;
        requestModel=new CheckoutDetailRequestModel(transactionId);
    }

    private void initView(View view){
        bottomSheet=view.findViewById(R.id.bottom_sheet);
        progressBarContainer=view.findViewById(R.id.progress_circular);
        dragHandle=view.findViewById(R.id.drag_handle);
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
            public void onResponse(@NonNull Call<BaseResponse<CheckoutDetailModel>> call, @NonNull Response<BaseResponse<CheckoutDetailModel>> response) {
                if(response.isSuccessful()){
                    List<BankPaymentMethodModel> bankPaymentMethodModelList=
                                (   response.body() !=null &&
                                    response.body().getData() !=null &&
                                    response.body().getData().getTransInfo() !=null &&
                                        response.body().getData().getTransInfo().getBankPaymentMethod() !=null
                                    ) ?

                            response.body().getData().getTransInfo().getBankPaymentMethod() : new ArrayList<>();
                    //TransactionInfo
                    TransactionInfoModel transactionInfoModel=
                                         (
                                            response.body() !=null &&
                                            response.body().getData() !=null &&
                                            response.body().getData().getTransInfo()!=null
                                            ) ?
                            response.body().getData().getTransInfo() : new TransactionInfoModel();

                    //connect to socket
                    connectSocketIO(transactionInfoModel.getTranNo());

                    //CheckoutPageConfig
                    CheckoutPageConfigModel checkoutPageConfigModel=
                            (
                                    response.body()!=null &&
                                            response.body().getData() !=null &&
                                            response.body().getData().getCheckoutPageConfig() !=null
                                    ) ?
                                    response.body().getData().getCheckoutPageConfig() : new CheckoutPageConfigModel();


                        //set bottomsheet radius
                        setRadiusCorner(getContext(),bottomSheet,10,checkoutPageConfigModel);

                        //set drag handle
                    if(isLightMode){
                        //LightModeModel lightModeModel=checkoutPageConfigModel.getAppearance().getLightMode();

                        createDraghanle(Color.parseColor("#E0E0E0"));
                    }else {
                        //DarkModeModel darkModeModel=checkoutPageConfigModel.getAppearance().getDarkMode();

                        createDraghanle(Color.parseColor("#E0E0E0"));
                    }

                        if(!bankPaymentMethodModelList.isEmpty()){
                        setSharePreference(bankPaymentMethodModelList,transactionInfoModel,checkoutPageConfigModel);//Store value in sharePreference
                        showFragment(new PaymentMethodFragment());//Go to Fragment PaymentMethod
                    }

                    new Handler().postDelayed(() -> {
                        hideProgressIndicator();//Hide Progress Indicator
                    },100);
                }
            }
            @Override
            public void onFailure(@NonNull Call<BaseResponse<CheckoutDetailModel>> call, @NonNull Throwable t) {

                Toast.makeText(getContext(),"Error internal server",Toast.LENGTH_LONG).show();

                Handler handler=new Handler();
                handler.postDelayed(() -> dialog.dismiss(),4000);

            }
        });
    }

    private void connectSocketIO(String roomName){
        SocketManager.connectJoinRoom(roomName);
    }

    private void setSharePreference(
            List<BankPaymentMethodModel> bankPaymentMethodModelList,
            TransactionInfoModel transactionInfoModel,
            CheckoutPageConfigModel checkoutPageConfigModel){
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Constant.KEY_PAYMENT_METHOD, SharePreferenceCustom.convertListObjectToJson(bankPaymentMethodModelList));
        editor.putString(Constant.KEY_LANGUAGE_CODE,language);
        editor.putString(Constant.KEY_REFERER_KEY,refererKey);
        editor.putString(Constant.KEY_TRANSACTION_INFO,SharePreferenceCustom.convertObjectToJson(transactionInfoModel));
        editor.putString(Constant.KEY_CHECKOUT_PAGE_CONFIG,SharePreferenceCustom.convertObjectToJson(checkoutPageConfigModel));
        editor.putBoolean(Constant.IS_LIGHT_MODE,isLightMode);
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(language==null || language.isEmpty()){
            language= LanguageCode.KH;
        }
    }

    private void broadcastFromSocketServer(){
        Socket socket=SocketManager.getSocket();
        socket.on(EVentName.BROADCAST, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("cccccccc", "call: BroadCast From SerVer"+args);
               String object=args[0].toString();
               SocketRespModel socketRespModel=SharePreferenceCustom.converJsonToObject(object,SocketRespModel.class);
                try {
                    RequestAPI requestAPI=new RequestAPI(refererKey);
                    CheckoutDetailRequestModel request=new CheckoutDetailRequestModel(socketRespModel.getTranNo());
                    Call<BaseResponse<CheckoutDetailModel>> call=requestAPI.postCheckoutDetail(request);
                    call.enqueue(new Callback<BaseResponse<CheckoutDetailModel>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<CheckoutDetailModel>> call, Response<BaseResponse<CheckoutDetailModel>> response) {

                            if(response.isSuccessful()){
                                Intent intent=new Intent(getActivity(),activityClass);
                                intent.putExtra("MyData", "Hello from SDK");
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<BaseResponse<CheckoutDetailModel>> call, Throwable t) {

                        }
                    });

                }catch (Exception e){
                    Log.d("TAG", "call: "+e.getMessage());
                }

            }
        });
    }

    public void setRadiusCorner(Context context, ViewGroup viewGroup, float cornerRadiusDP, CheckoutPageConfigModel checkoutPageConfigModel) {
        int cornerRadiusPX = (int) (cornerRadiusDP * context.getResources().getDisplayMetrics().density);

        float[] outerRadii = new float[] {
                cornerRadiusPX, cornerRadiusPX, cornerRadiusPX, cornerRadiusPX, 0, 0, 0, 0
        };

        if(isLightMode){
            ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
            String bottomSheetColor=checkoutPageConfigModel.getAppearance().getLightMode().getSecondaryColor().getBackgroundColor();
            String bottomSheetHexa= ConvertColorHexa.convertHex(bottomSheetColor);

            shapeDrawable.getPaint().setColor(Color.parseColor(bottomSheetHexa)); // Set your background color here
            viewGroup.setBackground(shapeDrawable);
        }else {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
            String bottomSheetColor=checkoutPageConfigModel.getAppearance().getDarkMode().getSecondaryColor().getBackgroundColor();
            String bottomSheetHexa= ConvertColorHexa.convertHex(bottomSheetColor);
            shapeDrawable.getPaint().setColor(Color.parseColor(bottomSheetHexa)); // Set your background color here
            viewGroup.setBackground(shapeDrawable);
        }


    }

    private void bottomSheetDialog(){
         dialog= (BottomSheetDialog) getDialog();
        int blackColorWith65PercentAlpha = Color.argb(Color.alpha(R.color.barrier_color),0,0,0 );

        //Set Barrier Color
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(blackColorWith65PercentAlpha));

//
        // Change BottomSheet Background Transparent
//           FrameLayout bottomSheet=d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//           int transparentColor = Color.argb(0, 0, 0, 0);
//           bottomSheet.setBackgroundColor(transparentColor);

        //Set Height to BottomSheet
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = dialog.getBehavior();
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        //Set Max Height
        bottomSheetBehavior.setMaxHeight((int)(screenHeight*0.9));
        //Set Height When Dialog Load
        bottomSheetBehavior.setPeekHeight((int) (screenHeight*1)); // Set the initial peek height
        bottomSheetBehavior.setHideable(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_layout,container,false);


        initView(view);
        showProgressIndicator();




        return view;
    }


    private void createDraghanle(int colorCode){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setTint(colorCode);
        gradientDrawable.setSize(82,5);// Set the stroke color and width
        gradientDrawable.setCornerRadius(20); // Set the corner radius
        gradientDrawable.setDither(true);

        dragHandle.setBackground(gradientDrawable);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postCheckoutDetail();

        //wait broadcast from server
        broadcastFromSocketServer();

        //
        bottomSheetDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SocketManager.disConnect();//Disconnect from Socket server

    }
}
