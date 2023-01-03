package com.hyl.accountbook;

import com.hyl.dao.DBOpenHelper;
import com.hyl.util.pubFun;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResPwdActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPwd;
    private EditText editResPwd;
    private Button btnConfirm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_password);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPwd = (EditText) findViewById(R.id.editPwd);
        editResPwd = (EditText) findViewById(R.id.editResPwd);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
    }

    public void OnMyConfirmClick(View v) {
        confirmInfo();
    }

    private void confirmInfo() {
        if(pubFun.isEmpty(editPhone.getText().toString()) || pubFun.isEmpty(editPwd.getText().toString()) || pubFun.isEmpty(editResPwd.getText().toString())){
            Toast.makeText(this, "手機號或密碼不能為空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!editPwd.getText().toString().equals(editResPwd.getText().toString())){
            Toast.makeText(this, "輸入密碼不正確！", Toast.LENGTH_SHORT).show();
            return;
        }

        //调用DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=?",new String[]{editPhone.getText().toString()},null,null,null);
        if(c!=null && c.getCount() >= 1){
            ContentValues cv = new ContentValues();
            cv.put("pwd", editPwd.getText().toString());
            String[] args = {String.valueOf(editPhone.getText().toString())};
            long rowid = db.update("user_tb", cv, "userID=?",args);
            c.close();
            db.close();
            Toast.makeText(this, "密碼重設成功！", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("用戶不存在，請到註冊頁面進行註冊！")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setResult(RESULT_OK);
                            Intent intent=new Intent(ResPwdActivity.this,RegistActivity.class);
                            ResPwdActivity.this.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    })
                    .show();
        }
    }
}