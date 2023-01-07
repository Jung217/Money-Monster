package com.hyl.accountbook;

import com.hyl.util.pubFun;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpendingActivity extends AppCompatActivity implements View.OnClickListener {
    //private TextView title;
    private TextView tv_year;
    private TextView item_detail, item_category_report;
    private Button btnAddRecord;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private Spinner month_spinner;
    private ArrayAdapter<String> month_adapter;

    private AlertDialog alertDialog_AddRecord;

    String[] titles = new String[]{"明細", "類別報表"};
    private static final String[] yearList = {pubFun.getTime("Y") + "年"};
    private static final String[] monthList = { "01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.spending);
        initViews();
        initSpinner();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        item_detail.setTextColor(Color.parseColor("#1ba0e1"));

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initViews() {
        //title = (TextView) findViewById(R.id.title);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_year.setText(yearList[0]);

        btnAddRecord = (Button) findViewById(R.id.btnAddRecord);

        item_detail = (TextView) findViewById(R.id.item_detail);
        item_category_report = (TextView) findViewById(R.id.item_category_report);

        item_detail.setOnClickListener(this);
        item_category_report.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
    }

    private void initSpinner(){
        month_spinner = (Spinner) findViewById(R.id.month_spinner);
        month_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthList);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(month_adapter);
        month_spinner.setOnItemSelectedListener(new month_spinnerSelectedListener());
        month_spinner.setSelection(pubFun.getTime("M"), true);
        month_spinner.setVisibility(View.VISIBLE);
    }

    class month_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            int pos = month_spinner.getSelectedItemPosition();
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_detail:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_category_report:
                vp.setCurrentItem(1, true);
                break;
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    private void changeTextColor(int position) {
        if (position == 0) {
            item_detail.setTextColor(Color.parseColor("#1ba0e1"));
            item_category_report.setTextColor(Color.parseColor("#000000"));
            btnAddRecord.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            item_category_report.setTextColor(Color.parseColor("#1ba0e1"));
            item_detail.setTextColor(Color.parseColor("#000000"));
            btnAddRecord.setVisibility(View.GONE);
        }
    }

    public void OnAddRecordClick(View v){
        final String[] items = {"收入", "支出"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("請選擇添加類別");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(SpendingActivity.this, items[i], Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SpendingActivity.this,ExpenseProcesActivity.class);
                intent.putExtra("strType", i);
                SpendingActivity.this.startActivity(intent);
                alertDialog_AddRecord.dismiss();
            }
        });
        alertDialog_AddRecord = alertBuilder.create();
        alertDialog_AddRecord.show();
    }
}
