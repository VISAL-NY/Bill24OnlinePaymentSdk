package com.bill24.onlinepaymentsdk.helper;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.bill24.onlinepaymentsdk.R;
import com.bill24.onlinepaymentsdk.model.conts.LanguageCode;


public class SetFont {
    public Typeface setFont(Context context, String languageCode){
        Typeface typeface;
        if (languageCode.equals(LanguageCode.EN)){
            typeface= ResourcesCompat.getFont(context, R.font.roboto_regular);
        }
        else {
            typeface=ResourcesCompat.getFont(context,R.font.battambang_regular);
        }
        return typeface;
    }
}
