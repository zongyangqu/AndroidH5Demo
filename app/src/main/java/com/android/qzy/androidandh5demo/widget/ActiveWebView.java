package com.android.qzy.androidandh5demo.widget;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/3/13
 *
 * 类描述：类描述：js和android通信桥梁WebView，封装js和安卓通信机制
 */

public class ActiveWebView extends WebView {

    /***
     * js调用android方法的映射字符串
     **/
    private static final String JS_INTERFACE = "jsInterface";

    public ActiveWebView(Context context) {
        super(context);
    }

    public ActiveWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActiveWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 判断是否是主线程
     * @return
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 注册js和android通信桥梁对象
     *
     * @param obj 桥梁类对象,该对象提供方法让js调用,默认开启JavaScriptEnabled=true
     */
    public void addBridgeInterface(Object obj) {
        this.getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new JSMethodInterface(obj), JS_INTERFACE);
    }

    /**
     * 内置js桥梁类
     * Created by youliang.ji on 2016/12/23.
     */

    public class JSMethodInterface {

        private Object mTarget;
        private Method targetMethod;
        public JSMethodInterface(Object targer) {
            this.mTarget = targer;
        }
        /**
         * 内置桥梁方法  所有JS调用的入口方法，根据Json返回数据在利用反射调用具体实现
         * 通过JavascriptInterface注解后方法会执行到子线程中
         * @param method 方法名
         * @param json   js传递参数，json格式
         */
        @JavascriptInterface
        public void invokeMethod(String method, String[] json) {
            Log.d("invokeThread---->",String.valueOf(isMainThread()));
            Class<?>[] params = new Class[]{String[].class};
            try {
                Method targetMethod = this.mTarget.getClass().getDeclaredMethod(method, params);
                targetMethod.invoke(mTarget, new Object[]{json});//反射调用js传递过来的方法，传参
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

