package com.vos_initiales.projet_integrateur;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VoirCV extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_cv);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()); 

        String url = getIntent().getStringExtra("url_cv");

        if (url != null && !url.isEmpty()) {
            if (url.endsWith(".pdf")) {

                webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
            } else {
                webView.loadUrl(url);
            }
        } else {
            Toast.makeText(this, "URL invalide", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
