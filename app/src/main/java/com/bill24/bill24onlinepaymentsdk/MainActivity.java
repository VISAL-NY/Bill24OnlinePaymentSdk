package com.bill24.bill24onlinepaymentsdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.model.main.Bill24OnlinePayment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String transactionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Button button=findViewById(R.id.button);
        Spinner spinner=findViewById(R.id.transaction_id);



        List<String> items=new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                transactionId=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(view -> {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext());
//                bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);
//                bottomSheetDialog.show();
//                BottomSheet bottomSheet=new BottomSheet();
//                bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());

            transactionId="67f74e8a-9ab6-4bc7-a265-7ee743fea18d";
            Bill24OnlinePayment bill24OnlinePayment=new Bill24OnlinePayment();
            bill24OnlinePayment.showBottomSheet(getSupportFragmentManager(),transactionId,"123","en");






        });
    }
}