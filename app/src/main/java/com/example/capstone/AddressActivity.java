package com.example.capstone;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AddressActivity extends AppCompatActivity {
    public static final int ADDRESS_REQUEST_CODE = 2928;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new KaKaoJavaScriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:execKakaoPostcode();");
            }
        });

        // Kakao에서 https를 허용하지 않아서 https -> http로 바꿔야 동작함 (중요!!)
        webView.loadUrl("http://nsj1201.dothome.co.kr/searchAddress/searchAddress.html");
    }

    public class KaKaoJavaScriptInterface {
        @JavascriptInterface
        public void processDATA(String address) {
            Intent intent = new Intent();
            intent.putExtra("address", address);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
