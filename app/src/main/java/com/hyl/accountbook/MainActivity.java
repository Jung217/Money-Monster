package com.hyl.accountbook;

import com.hyl.accountbook.CircleMenuLayout.OnMenuItemClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long mExitTime;

    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] { "登錄&註冊", "關於我們", "許願墻",
            "QR 掃描", "收入&支出", "統計" };
    private int[] mItemImgs = new int[] { R.mipmap.home_mbank_1_normal,
            R.mipmap.home_mbank_2_normal, R.mipmap.home_mbank_3_normal,
            R.mipmap.home_mbank_4_normal, R.mipmap.home_mbank_5_normal,
            R.mipmap.home_mbank_6_normal };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void itemClick(View view, int pos) {
                if (mItemTexts[pos] == "QR 掃描") {
                    openSettingWind(view);
                } else if (mItemTexts[pos] == "收入&支出") {
                    openSpendingWind(view);
                } else if (mItemTexts[pos] == "登錄&註冊") {
                    openLoginWind(view);
                } else if (mItemTexts[pos] == "統計") {
                    openCountWind(view);
                } else if (mItemTexts[pos] == "關於我們") {
                    openAboutUsAddWind(view);
                } else if (mItemTexts[pos] == "許願墻") {
                    openWishWind(view);
                }
                ;
            }

            public void itemCenterClick(View view) {
                Toast.makeText(MainActivity.this, "you can login or register and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openLoginWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    private void openSpendingWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SpendingActivity.class);
        this.startActivity(intent);
    }

    private void openCountWind(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, CountActivity.class);
        this.startActivity(intent);
    }

    private void openSettingWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SettingActivity.class);
        this.startActivity(intent);
    }

    private void openWishWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, WishActivity.class);
        this.startActivity(intent);
    }

    private void openAboutUsAddWind(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, AboutUsActivity.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出吃錢怪獸", Toast.LENGTH_SHORT).show();
                //System.currentTimeMillis()系统当前时间
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
