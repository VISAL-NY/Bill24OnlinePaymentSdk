package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;

public class ExpireFragment extends Fragment {
    private AppCompatButton buttonTryAgain;
    private AppCompatTextView textTranExpired,textTryAgain;
    private String transactionId,refererKey,language;
    public ExpireFragment(String transactionId){
      this.transactionId=transactionId;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        //get language
        language=preferences.getString(Constant.KEY_LANGUAGE_CODE,"");
        //get refererKey
        refererKey=preferences.getString(Constant.KEY_REFERER_KEY,"");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.expire_fragment_layout,container,false);
        initView(view);
        //Update Font
        updateFont();

        buttonTryAgain.setOnClickListener(v->{
            Fragment fragment=getParentFragment();
            if(fragment !=null && fragment instanceof BottomSheet){
                ((BottomSheet)getParentFragment()).showFragment(new KhqrFragment(transactionId));
            }
        });

        return view;
    }
}
