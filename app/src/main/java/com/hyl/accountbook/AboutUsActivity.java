package com.hyl.accountbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {
    private TextView tvAboutUs;
    private TextView tvIntroduce;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
    }
}

