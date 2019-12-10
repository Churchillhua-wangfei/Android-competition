package com.lenovo.manufacture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TextActivity07 extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text07);
        webView=findViewById(R.id.webview7);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/Demo09.html");
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
