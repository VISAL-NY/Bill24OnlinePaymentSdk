package com.bill24.bill24onlinepaymentsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.model.main.Bill24OnlinePayment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext());
//                bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);
//                bottomSheetDialog.show();

//                BottomSheet bottomSheet=new BottomSheet();
//                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());

                Bill24OnlinePayment bill24OnlinePayment=new Bill24OnlinePayment();
                bill24OnlinePayment.showBottomSheet(getSupportFragmentManager(),"1");
            }
        });
    }
}