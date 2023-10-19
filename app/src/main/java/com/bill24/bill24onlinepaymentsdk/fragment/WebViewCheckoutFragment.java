package com.bill24.bill24onlinepaymentsdk.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bill24.bill24onlinepaymentsdk.R;

public class WebViewCheckoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.webview_web_checkout,container,false);
        WebView webView=view.findViewById(R.id.web_view_web_checkout);
        ProgressBar progressBar=view.findViewById(R.id.progress_loading_webview);
        RelativeLayout loadingContainer=view.findViewById(R.id.web_view_loading_container);

        //Get Screen Height
        DisplayMetrics displayMetrics=new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight=displayMetrics.heightPixels;

        ViewGroup.LayoutParams  layoutParams=loadingContainer.getLayoutParams();
        layoutParams.height=(int)(screenHeight*0.9);//Set Layout to 90%
        loadingContainer.setLayoutParams(layoutParams);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                   progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    //progressBar.setProgress(newProgress);
                }
            }
        });

        webView.loadUrl("https://www.w3schools.com/");
        return view;
    }
}
