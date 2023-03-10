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
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        getData();

        adapter = new SimpleAdapter(getActivity()
                , listitem
                , R.layout.fragment_one_item
                , new String[]{"expense_category", "expense_money", "image_expense"}
                , new int[]{R.id.tv_expense_category, R.id.tv_expense_money, R.id.image_expense});

        listView = (ListView) v.findViewById(R.id.lv_expense);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), map.get("expense_category").toString(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private void getData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("userID", "");

        Log.i("info", "????????????????????????" + userID);

        if(userID.isEmpty()){
            new AlertDialog.Builder(getActivity())
                    .setTitle("??????")
                    .setMessage("??????????????????????????????????????????????????????")
                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getActivity().setResult(-1);
                            Intent intent=new Intent(getActivity(),LoginActivity.class);
                            getActivity().startActivity(intent);
                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
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

