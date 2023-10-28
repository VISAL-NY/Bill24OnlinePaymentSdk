package com.bill24.onlinepaymentsdk.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.bill24.onlinepaymentsdk.model.conts.LanguageCode;

import java.util.Locale;

public class ChangLanguage {
    public static void setLanguage(String languageCode,Context context){

        Locale locale;
        if(languageCode.equals(LanguageCode.EN)){
            locale=new Locale(LanguageCode.EN);
        }else {
            locale=new Locale(LanguageCode.KH);
        }
        Locale.setDefault(locale);
        Resources resources=context.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.locale=locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}
