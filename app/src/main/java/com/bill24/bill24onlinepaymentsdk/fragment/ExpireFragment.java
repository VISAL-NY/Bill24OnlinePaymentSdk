package com.bill24.bill24onlinepaymentsdk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;

public class ExpireFragment extends Fragment {
    private AppCompatButton buttonTryAgain;
    private String languageCode;
    public ExpireFragment(String languageCode){
        this.languageCode=languageCode;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.expire_fragment_layout,container,false);
        buttonTryAgain=view.findViewById(R.id.button_try_again);
        buttonTryAgain.setOnClickListener(v->{
            Toast.makeText(getContext(),"Button Click",Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
