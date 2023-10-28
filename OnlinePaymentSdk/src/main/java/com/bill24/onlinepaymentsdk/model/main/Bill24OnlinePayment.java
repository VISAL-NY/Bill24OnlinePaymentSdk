package com.bill24.onlinepaymentsdk.model.main;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;

import com.bill24.onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;

public class Bill24OnlinePayment {
    public Bill24OnlinePayment(){
    }
    public static void init(
            FragmentManager fragmentManager,
            String transactionId,
            String refererKey,
            boolean isLightMode,
            Class<? extends Activity> activityClass,
            String language){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey,isLightMode,activityClass,language);
        bottomSheet.show(fragmentManager,bottomSheet.getTag());
    }

    public static void init(
            FragmentManager fragmentManager,
            String transactionId,
            String refererKey,
            boolean isLightMode,
            Class<? extends Activity> activityClass){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey,isLightMode,activityClass);
        bottomSheet.show(fragmentManager, bottomSheet.getTag());
    }

}
