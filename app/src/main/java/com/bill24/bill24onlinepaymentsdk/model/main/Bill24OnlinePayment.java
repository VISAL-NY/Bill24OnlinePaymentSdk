package com.bill24.bill24onlinepaymentsdk.model.main;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;

public class Bill24OnlinePayment {
    public Bill24OnlinePayment(){
    }
    public void showBottomSheet(FragmentManager fragmentManager,String transactionId,String refererKey,String language){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey,language);
        bottomSheet.show(fragmentManager,bottomSheet.getTag());
    }

    public void showBottomSheet(FragmentManager fragmentManager,String transactionId,String refererKey){
        BottomSheet bottomSheet=new BottomSheet(transactionId,refererKey);
        bottomSheet.show(fragmentManager, bottomSheet.getTag());
    }

}
