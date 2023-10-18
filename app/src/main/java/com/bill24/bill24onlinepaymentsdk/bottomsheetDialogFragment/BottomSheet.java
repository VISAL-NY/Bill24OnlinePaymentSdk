package com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.fragment.PaymentMethodFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog dialog= (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d= (BottomSheetDialog) dialogInterface;

            int alpha = (int) (0.65 * 255);
            int blackColorWith65PercentAlpha = Color.argb(alpha, 0, 0, 0);

            //Set Barrier Color
            d.getWindow().setBackgroundDrawable(new ColorDrawable(blackColorWith65PercentAlpha));

//
        // Change BottomSheet Background Transparent
//           FrameLayout bottomSheet=d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//           int transparentColor = Color.argb(0, 0, 0, 0);
//           bottomSheet.setBackgroundColor(transparentColor);

            //Set Height to BottomSheet
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = d.getBehavior();
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            //Set Max Height
            bottomSheetBehavior.setMaxHeight((int)(screenHeight*0.9));
            //Set Height When Dialog Load
            bottomSheetBehavior.setPeekHeight((int) (screenHeight*1)); // Set the initial peek height
            bottomSheetBehavior.setHideable(true);

        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showFragment(new PaymentMethodFragment("kh"));
    }

    public void showFragment(Fragment fragment){
//        Animation enterAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.enter_animation);
////        Animation exitAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.exit_animation);
//        FragmentManager fragmentManager=getChildFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(
//                R.anim.enter_animation,  // Enter animation
//                //R.anim.exit_animation,   // Exit animation
//                R.anim.enter_animation // Pop-enter animation (for back stack)
//                //R.anim.exit_animation    // Pop-exit animation (for back stack)
//        );
//        fragmentTransaction.replace(R.id.content_layout,fragment).commit();

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout,fragment)
                .commit();


    }
}
