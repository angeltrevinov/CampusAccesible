package com.example.campusaccesible;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String webPageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        this.webPageUrl = intent.getStringExtra(AsistenciaAdapter.CURRENT_ASISTENCIA);

        this.getSupportActionBar().setTitle("Asistencia");
        this.webView = findViewById(R.id.web_view);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.loadUrl(this.webPageUrl);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}