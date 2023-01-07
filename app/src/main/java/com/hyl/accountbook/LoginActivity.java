package com.hyl.accountbook;

import com.hyl.util.pubFun;
import com.hyl.dao.DBOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editPhone;
    private EditText editPwd;
    private Button btnLogin;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPwd = (EditText) findViewById(R.id.editPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void OnMyLoginClick(View v){
        if(pubFun.isEmpty(editPhone.getText().toString()) || pubFun.isEmpty(editPwd.getText().toString())){
            Toast.makeText(this, "手機或密碼不可空！", Toast.LENGTH_SHORT).show();
            return;
        }

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=? and pwd=?",new String[]{editPhone.getText().toString(),editPwd.getText().toString()},null,null,null);
        if(c!=null && c.getCount() >= 1){
            String[] cols = c.getColumnNames();
            while(c.moveToNext()){
                for(String ColumnName:cols){
                    Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                }
            }
            c.close();
            db.close();

            SharedPreferences mySharedPreferences= getSharedPreferences("setting",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("userID", editPhone.getText().toString());
            editor.commit();

            this.finish();
        }
        else{
            Toast.makeText(this, "手機號或密碼輸入錯誤！", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnMyRegistClick(View v)  {
        Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
        //intent.putExtra("info", "No66778899");
        LoginActivity.this.startActivity(intent);
    }

    public void OnMyResPwdClick(View v){
        Intent intent=new Intent(LoginActivity.this,ResPwdActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}