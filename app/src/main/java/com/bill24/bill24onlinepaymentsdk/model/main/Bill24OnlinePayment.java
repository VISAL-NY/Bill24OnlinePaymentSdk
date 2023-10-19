package com.bill24.bill24onlinepaymentsdk.model.main;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;

public class Bill24OnlinePayment {
    public Bill24OnlinePayment(){
    }
    public void showBottomSheet(FragmentManager fragmentManager,String transactionId){
        BottomSheet bottomSheet=new BottomSheet(transactionId);
        bottomSheet.show(fragmentManager,bottomSheet.getTag());
    }

}
