package com.olituc.designshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class modifyPassWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass_word);
    }

    public void ensureModifyPassWord(View view){
        /**
         * 提交服务器数据根据返回值判断是否修改成功
         */
        Toast.makeText(getApplicationContext(),"ok", Toast.LENGTH_SHORT).show();
    }

    public void cancelModifyPassWord(View view){
        this.finish();
    }
}
