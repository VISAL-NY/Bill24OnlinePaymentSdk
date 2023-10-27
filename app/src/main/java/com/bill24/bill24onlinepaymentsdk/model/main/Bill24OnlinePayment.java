package com.bill24.bill24onlinepaymentsdk.model.main;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;

public class Bill24OnlinePayment {
    public Bill24OnlinePayment(){
    }
    public static void showBottomSheet(
            FragmentManager fragmentManager,
            String transactionId,
            String refererKey,
            boolean isLightMode,
            Class<? extends Activity> activityClass,
            String language){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey,isLightMode,activityClass,language);
        bottomSheet.show(fragmentManager,bottomSheet.getTag());
    }

    public static void showBottomSheet(
            FragmentManager fragmentManager,
            String transactionId,
            boolean isLightMode,
            String refererKey,
            Class<? extends Activity> activityClass){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey,isLightMode,activityClass);
        bottomSheet.show(fragmentManager, bottomSheet.getTag());
    }

}
