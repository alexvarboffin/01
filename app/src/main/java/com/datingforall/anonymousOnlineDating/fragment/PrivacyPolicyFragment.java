package com.datingforall.anonymousOnlineDating.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.datingforall.anonymousOnlineDating.R;

public class PrivacyPolicyFragment extends BaseFragment {

    private String link = "https://docs.google.com/document/d/1n4L4R4nRZpBxSv6jy25N8zkl2Q9f3qpkE84oRYRSFY0/edit?usp=sharing";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByClassName('docs-ml-header')[0];" +
                        "head.parentNode.removeChild(head);" +
                        "})()");
            }
        });
        webView.loadUrl(link);
    }
}
