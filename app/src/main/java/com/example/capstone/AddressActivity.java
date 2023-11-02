package com.example.capstone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AddressActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.addJavascriptInterface(new KaKaoJavaScriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // SSL 에러가 발생해도 계속 진행
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:sample2_execDaumPostcode();");

            }
        });

        //ssl 인증이 없는 경우 해결을 위한 부분
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        // Kakao에서 https를 허용하지 않아서 https -> http로 바꿔야 동작함 (중요!!)
        webView.loadUrl("http://nsj1201.dothome.co.kr/searchAddress/searchAddress.html");
    }

    class KaKaoJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
