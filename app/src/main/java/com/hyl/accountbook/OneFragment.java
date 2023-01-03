package com.hyl.accountbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hyl.dao.DBOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneFragment extends Fragment {

    ListView listView;
    SimpleAdapter adapter;

    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
    int[] image_expense = new int[]{R.mipmap.detail_income, R.mipmap.detail_payout };

    private boolean isFirstLoading = true;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        /*測試
        int[] image_expense = new int[]{R.mipmap.detail_income, R.mipmap.detail_payout };
        String[] expense_category = new String[] {"發薪水", "買衣服"};
        String[] expense_money = new String[] {"30000.00", "1500.00"};

        for (int i = 0; i < image_expense.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image_expense", image_expense[i]);
            map.put("expense_category", expense_category[i]);
            map.put("expense_money", expense_money[i]);
            listitem.add(map);
        }*/
        getData();

        adapter = new SimpleAdapter(getActivity()
                , listitem
                , R.layout.fragment_one_item
                , new String[]{"expense_category", "expense_money", "image_expense"}
                , new int[]{R.id.tv_expense_category, R.id.tv_expense_money, R.id.image_expense});

        listView = (ListView) v.findViewById(R.id.lv_expense);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), map.get("expense_category").toString(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    /*Runnable add = new Runnable(){
        @Override
        public void run() {
            Map<String, Object> map = new HashMap<>();
            map.put("image_expense", R.mipmap.detail_income);
            map.put("expense_category", "aaaaaa");
            map.put("expense_money", "+10");
            listitem.add(map);
            adapter.notifyDataSetChanged();
        }
    };*/

    private void getData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("userID", "");

        Log.i("info", "此次登錄的用戶是" + userID);

        if(userID.isEmpty()){
            new AlertDialog.Builder(getActivity())
                    .setTitle("提示")
                    .setMessage("您還未登錄，請點擊確定按鈕進行登錄！")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getActivity().setResult(-1);
                            Intent intent=new Intent(getActivity(),LoginActivity.class);
                            getActivity().startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    })
                    .show();
        }else{
            //call DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();

            Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);
            c.moveToFirst();
            int iColCount = c.getColumnCount();
            int iNumber = 0;
            String strType = "";
            listitem.clear();
            while (iNumber < c.getCount()){
                Map<String, Object> map = new HashMap<String, Object>();

                strType = c.getString(c.getColumnIndex("Type"));
                map.put("image_expense", image_expense[Integer.parseInt(strType)]);
                map.put("expense_category", c.getString(c.getColumnIndex("item")));
                if(strType.equals("0")){
                    map.put("expense_money", "+" + c.getString(c.getColumnIndex("cost")));
                }else{
                    map.put("expense_money", "-" + c.getString(c.getColumnIndex("cost")));
                }

                c.moveToNext();
                listitem.add(map);
                iNumber++;
                System.out.println(map);
            }
            c.close();
            db.close();
        }
    }
    //renew data
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            getData();
            adapter.notifyDataSetChanged();
        }

        isFirstLoading = false;
    }
}

