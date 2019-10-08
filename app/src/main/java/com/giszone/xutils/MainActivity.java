package com.giszone.xutils;

import android.os.Bundle;

import com.giszone.utils.ToastUtils;
import com.giszone.utils.XUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XUtils.init(this);

        ToastUtils.setResultToToast("ahahahhaha");
    }
}
