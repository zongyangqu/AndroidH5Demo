package com.android.qzy.androidandh5demo.ui;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.qzy.androidandh5demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：quzongyang
 * 创建时间：2017/3/6
 * 类描述：
 */

public class BaseLoadActivity extends AppCompatActivity {

    @Bind(R.id.btn_load)
    Button btn_load;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.loadingBar)
    ProgressBar loadingBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_load);
        ButterKnife.bind(this);
        //1.首先在WebView初始化时添加如下代码
        if(Build.VERSION.SDK_INT >= 19) {
            /**对系统API在19以上的版本作了兼容。
             * 因为4.4以上系统在onPageFinished时再恢复图片加载时,如果存在多张图片引用的是相同的src时，
             * 会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。*/
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
    }


    @OnClick({R.id.btn_load})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.btn_load:
                //直接加载HTML代码
                //webView.loadDataWithBaseURL(null, htmlCode, "text/html", "utf-8", null);
                //加载网络资源(注意要加上网络权限)
                webView.loadUrl("https://www.baidu.com/");
                //加载assets目录下的test.html文件
                //webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if("https://www.baidu.com/".equals(url))                   {
                            view.loadUrl("http://www.jikedaohang.com/");
                        }
                        return false;
                    }
                    //2.在WebView的WebViewClient子类中重写onPageFinished()方法添加如下代码：
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        if(!webView.getSettings().getLoadsImagesAutomatically()) {
                            webView.getSettings().setLoadsImagesAutomatically(true);
                        }
                    }
                });

                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        Toast.makeText(BaseLoadActivity.this,title,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        loadingBar.setProgress(newProgress);
                        if (newProgress == 100) {
                            loadingBar.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }

    /**
     * 重写返回键  返回WebView上个浏览界面
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
