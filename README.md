**Android与H5互调**


**实现基本步骤**
**1.首先在Android中添加WebView对JS的支持**

        WebSettings settings = webView.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);

**2.WebView加载H5资源**

我这里使用的是本地的H5资源，如果是加载网络H5方法也是一样的

       //加载本地html文件
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html")`
这里我贴出本地H5代码放在资源文件assets下（JavaAndJavaScriptCall.html）

    <html>
	<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <script type="text/javascript">
	function javaCallJs(arg){
		 document.getElementById("content").innerHTML =
	         ("欢迎："+arg );
	}
    
    </script>
	</head>
	<body>
	<div align="left" id="content"> 谷粉</div>
	<div align="right">光临尚硅谷</div>
	<p><img src="https://www.baidu.com/img/bd_logo1.png"></p>
	<input type="button" value="点击Android被调用" onclick="window.Android.showToast('JS中传来的参数')"/>
	</body>
	</html>
这里先看下Android关键字和showToast方法，下面会用到。
 

**3.WebView添加互调JS支持的接口（重点）**

webView.addJavascriptInterface(new JSInterface(),"Android");
private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(NativeH5Activity.this,arg,Toast.LENGTH_SHORT).show();
        }
    }
这里有两点需要注意
（1）. Android是可以随意命名的，但是必须和H5中JS的调用名保持一致（对应第二条中的Android）。
 
（2）.JsInterface的类名也是可以随意命名，重点是其中的方法名和注释。@JavascriptInterface 是固定格式，方法名showToast 要和JS中的方法名对应（对应第二条中的showToast）。

**4.Android调用H5**

    @OnClick({R.id.btn_submit})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.btn_submit://按钮的点击事件
                webView.loadUrl("javascript:javaCallJs(" + "'" + et_user.getText().toString()+"'"+")");
                break;
        }
    }
此时Android端可以调用H5的javaCallJs方法，H5端可以调用Android端的showToast方法。
                                                                                               看下效果

	
  ![](http://i.imgur.com/qqiLO1V.gif)

**贴下Android端完整代码**

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
