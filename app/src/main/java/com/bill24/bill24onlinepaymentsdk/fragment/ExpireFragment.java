package com.bill24.bill24onlinepaymentsdk.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;
import com.bill24.bill24onlinepaymentsdk.bottomsheetDialogFragment.BottomSheet;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.CustomShape;
import com.bill24.bill24onlinepaymentsdk.customShapeDrawable.SelectedState;
import com.bill24.bill24onlinepaymentsdk.helper.ChangLanguage;
import com.bill24.bill24onlinepaymentsdk.helper.ConvertColorHexa;
import com.bill24.bill24onlinepaymentsdk.helper.SetFont;
import com.bill24.bill24onlinepaymentsdk.helper.SharePreferenceCustom;
import com.bill24.bill24onlinepaymentsdk.model.CheckoutPageConfigModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.darkMode.DarkModeModel;
import com.bill24.bill24onlinepaymentsdk.model.appearance.lightMode.LightModeModel;
import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;

public class ExpireFragment extends Fragment {
    private FrameLayout buttonTryAgain;
    private AppCompatTextView textTryagainButton;
    private AppCompatTextView textTranExpired,textTryAgain,expireDashLine;
    private String transactionId,refererKey,language;
    private CheckoutPageConfigModel checkoutPageConfigModel;
    private LinearLayoutCompat expireContainer;
    private boolean isLightMode;
    public ExpireFragment(String transactionId){
      this.transactionId=transactionId;
    }
    private void initView(View view){
        buttonTryAgain=view.findViewById(R.id.button_try_again);
        textTranExpired=view.findViewById(R.id.text_expire_title);
        textTryAgain=view.findViewById(R.id.text_try_again_title);
        expireContainer=view.findViewById(R.id.expire_container);
        textTryagainButton=view.findViewById(R.id.text_button_try_again);
        expireDashLine=view.findViewById(R.id.expire_dash_line);
    }
    private void updateFont(){
        SetFont font=new SetFont();
        Typeface typeface=font.setFont(getContext(),language);
        textTranExpired.setTypeface(typeface);
        textTranExpired.setTextSize(16);
        textTranExpired.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);

        textTryAgain.setTypeface(typeface);
        textTryAgain.setTextSize(14);

        textTryagainButton.setTypeface(typeface);
        textTryagainButton.setTextSize(13);
    }

    private void applyStyleShapeLightMode(){
        LightModeModel lightModeModel=checkoutPageConfigModel.getAppearance().getLightMode();

        //expire container
        String bgExpireContainer=lightModeModel.getSecondaryColor().getBackgroundColor();
        String bgExpireContainerHexa= ConvertColorHexa.convertHex(bgExpireContainer);
        expireContainer.setBackgroundColor(Color.parseColor(bgExpireContainerHexa));

        //text transaction expire
        String tranExpireColor=lightModeModel.getPrimaryColor().getTextColor();
        String tranExpireColorHexa=ConvertColorHexa.convertHex(tranExpireColor);
        textTranExpired.setTextColor(Color.parseColor(tranExpireColorHexa));

        //text try again
        String tryAgainColor=lightModeModel.getSecondaryColor().getTextColor();
        String tryAgainColorHexa=ConvertColorHexa.convertHex(tryAgainColor);
        textTryAgain.setTextColor(Color.parseColor(tryAgainColorHexa));

        //shape button
        String bgButton=lightModeModel.getButton().getActionButton().getBackgroundColor();
        String bgButtonHexa=ConvertColorHexa.convertHex(bgButton);
        ShapeDrawable shape= CustomShape.applyShape(Color.parseColor(bgButtonHexa),20);

        String buttonSelectColor=ConvertColorHexa.getFiftyPercentColor(bgButton);
        ShapeDrawable buttonSelected=CustomShape.applyShape(Color.parseColor(buttonSelectColor),20);

        StateListDrawable selector= SelectedState.selectedSate(shape,buttonSelected);
        buttonTryAgain.setBackground(selector);

        //text button try again
        String textButtonColor=lightModeModel.getButton().getActionButton().getTextColor();
        String textButtonColorHexa=ConvertColorHexa.convertHex(textButtonColor);
        textTryagainButton.setTextColor(Color.parseColor(textButtonColorHexa));


        //dash line
        String dashLine=lightModeModel.getSecondaryColor().getTextColor();
        String dashLineHexa=ConvertColorHexa.convertHex(dashLine);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.LINE);
        gradientDrawable.setStroke(1,
                Color.parseColor(dashLineHexa),
                15,
                15); // Set the stroke color and width
        gradientDrawable.setCornerRadius(20); // Set the corner radius
        gradientDrawable.setDither(true);
        expireDashLine.setBackground(gradientDrawable);



    }
    private void applyStyleShapeDarkMode(){
        DarkModeModel darkModeModel=checkoutPageConfigModel.getAppearance().getDarkMode();

        //expire container
        String bgExpireContainer=darkModeModel.getSecondaryColor().getBackgroundColor();
        String bgExpireContainerHexa= ConvertColorHexa.convertHex(bgExpireContainer);
        expireContainer.setBackgroundColor(Color.parseColor(bgExpireContainerHexa));

        //text transaction expire
        String tranExpireColor=darkModeModel.getPrimaryColor().getTextColor();
        String tranExpireColorHexa=ConvertColorHexa.convertHex(tranExpireColor);
        textTranExpired.setTextColor(Color.parseColor(tranExpireColorHexa));

        //text try again
        String tryAgainColor=darkModeModel.getSecondaryColor().getTextColor();
        String tryAgainColorHexa=ConvertColorHexa.convertHex(tryAgainColor);
        textTryAgain.setTextColor(Color.parseColor(tryAgainColorHexa));

        //shape button
        String bgButton=darkModeModel.getButton().getActionButton().getBackgroundColor();
        String bgButtonHexa=ConvertColorHexa.convertHex(bgButton);
        ShapeDrawable shape= CustomShape.applyShape(Color.parseColor(bgButtonHexa),20);
        String buttonSelectColor=ConvertColorHexa.getFiftyPercentColor(bgButton);
        ShapeDrawable buttonSelected=CustomShape.applyShape(Color.parseColor(buttonSelectColor),20);

        StateListDrawable selector= SelectedState.selectedSate(shape,buttonSelected);
        buttonTryAgain.setBackground(selector);

        //text button try again
        String textButtonColor=darkModeModel.getButton().getActionButton().getTextColor();
        String textButtonColorHexa=ConvertColorHexa.convertHex(textButtonColor);
        textTryagainButton.setTextColor(Color.parseColor(textButtonColorHexa));


        //dash line
        String dashLine=darkModeModel.getSecondaryColor().getTextColor();
        String dashLineHexa=ConvertColorHexa.convertHex(dashLine);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.LINE);
        gradientDrawable.setStroke(1,
                Color.parseColor(dashLineHexa),
                15,
                15); // Set the stroke color and width// Set the corner radius
        gradientDrawable.setDither(true);
        expireDashLine.setBackground(gradientDrawable);

    }


    private  void  getSharePreference(){
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        //get language
        language=preferences.getString(Constant.KEY_LANGUAGE_CODE,"");
        //get refererKey
        refererKey=preferences.getString(Constant.KEY_REFERER_KEY,"");
        ChangLanguage.setLanguage(language,getContext());

        String checkoutPageConfigJson=preferences.getString(Constant.KEY_CHECKOUT_PAGE_CONFIG,"");
        checkoutPageConfigModel= SharePreferenceCustom.converJsonToObject(checkoutPageConfigJson, CheckoutPageConfigModel.class);

        isLightMode=preferences.getBoolean(Constant.IS_LIGHT_MODE,true);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSharePreference();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.expire_fragment_layout,container,false);
        initView(view);
        //Update Font
        updateFont();

        //apply style shape
        if(isLightMode){
                applyStyleShapeLightMode();
        }else {
            applyStyleShapeDarkMode();
        }


        buttonTryAgain.setOnClickListener(v->{
            Fragment fragment=getParentFragment();
            if(fragment !=null && fragment instanceof BottomSheet){
                ((BottomSheet)getParentFragment()).showFragment(new KhqrFragment(transactionId));
            }
        });

        return view;
    }
}
