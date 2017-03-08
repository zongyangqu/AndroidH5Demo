package com.android.qzy.androidandh5demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.qzy.androidandh5demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/3/6
 *
 * 类描述：
 */

public class NativeH5Activity extends AppCompatActivity{
    @Bind(R.id.ll_root)
    LinearLayout ll_root;
    @Bind(R.id.et_user)
    EditText et_user;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_h5);
        ButterKnife.bind(this);
        initWebView();
    }

    //初始化WebView
    private void initWebView() {
        //动态创建一个WebView对象并添加到LinearLayout中
        webView = new WebView(getApplication());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        ll_root.addView(webView);
        //不跳转到其他浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }

    private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(NativeH5Activity.this,arg,Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btn_submit})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.btn_submit://按钮的点击事件
                webView.loadUrl("javascript:javaCallJs(" + "'" + et_user.getText().toString()+"'"+")");
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
