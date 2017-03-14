package com.android.qzy.androidandh5demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.qzy.androidandh5demo.R;
import com.android.qzy.androidandh5demo.widget.ActiveWebView;
import com.android.qzy.androidandh5demo.widget.JavaSctiptMethods;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/3/13
 *
 * 类描述：WebView调用H5封装展示
 */

public class WebViewAssembleActivity extends AppCompatActivity{

    @Bind(R.id.webView)
    ActiveWebView webView;
    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_assemble);
        ButterKnife.bind(this);
        initView();
        setWebViewClient();
    }

    public void initView(){
        webView.addBridgeInterface(new JavaSctiptMethods(this,webView));
        webView.loadUrl("file:///android_asset/BridgeWebView/index.html");
    }

    private void setWebViewClient() {
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Toast.makeText(WebViewAssembleActivity.this,title,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                loadingBar.setProgress(newProgress);
                if (newProgress == 100) {
                    loadingBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
