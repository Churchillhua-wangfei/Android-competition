package com.lenovo.manufacture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TestActivity02 extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test02);
        webView=findViewById(R.id.webview2);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/Demo02.html");
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
