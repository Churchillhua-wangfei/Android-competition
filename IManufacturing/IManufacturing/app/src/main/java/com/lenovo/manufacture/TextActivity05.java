package com.lenovo.manufacture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TextActivity05 extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text05);
        webView=findViewById(R.id.webview5);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/Demo06.html");
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
