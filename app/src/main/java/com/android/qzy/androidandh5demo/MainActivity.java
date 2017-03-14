package com.android.qzy.androidandh5demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.qzy.androidandh5demo.ui.BaseLoadActivity;
import com.android.qzy.androidandh5demo.ui.NativeH5Activity;
import com.android.qzy.androidandh5demo.ui.WebViewAssembleActivity;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_base_load)
    Button btn_base_load;
    @Bind(R.id.btn_native)
    Button btn_native;
    @Bind(R.id.btnAssemble)
    Button btnAssemble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_base_load,R.id.btn_native,R.id.btnAssemble})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.btn_base_load:
                startActivity(new Intent(MainActivity.this, BaseLoadActivity.class));
                break;
            case R.id.btn_native:
                startActivity(new Intent(MainActivity.this, NativeH5Activity.class));
                break;
            case R.id.btnAssemble:
                startActivity(new Intent(MainActivity.this, WebViewAssembleActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
