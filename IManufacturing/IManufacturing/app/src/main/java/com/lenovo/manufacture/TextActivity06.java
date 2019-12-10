package com.lenovo.manufacture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TextActivity06 extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text06);
        webView=findViewById(R.id.webview6);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/Demo07.html");
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
