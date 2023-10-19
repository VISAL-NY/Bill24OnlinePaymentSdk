package com.bill24.bill24onlinepaymentsdk.fragment;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;

public class ExpireFragment extends Fragment {
    private AppCompatButton buttonTryAgain;
    private AppCompatTextView textTranExpired,textTryAgain;
    private String languageCode;
    public ExpireFragment(String languageCode){
        this.languageCode=languageCode;
    }


    private void initView(View view){
        buttonTryAgain=view.findViewById(R.id.button_try_again);
        textTranExpired=view.findViewById(R.id.text_expire_title);
        textTryAgain=view.findViewById(R.id.text_try_again_title);
    }

    private void updateFont(){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(getContext(),"km");
        textTranExpired.setTypeface(typeface);
        textTranExpired.setTextSize(16);
        textTranExpired.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);

        textTryAgain.setTypeface(typeface);
        textTryAgain.setTextSize(14);

        buttonTryAgain.setTypeface(typeface);
        buttonTryAgain.setTextSize(13);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.expire_fragment_layout,container,false);
        initView(view);
        //Update Font
        updateFont();

        buttonTryAgain.setOnClickListener(v->{
            Toast.makeText(getContext(),"Button Click",Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
