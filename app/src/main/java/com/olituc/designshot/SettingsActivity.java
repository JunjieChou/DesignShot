package com.olituc.designshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * 更换密码 复用忘记密码
     * @param view
     */
    public void changeLoginPassWord(View view){
        /**
         * 与忘记密码activity中的更换密码操作相同
         */
        Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show();
    }

    public void cancelLoginStatus(View view){
        /**
         * 跟服务器发送请求
         */
    }

    public void returnToMain(View view){
        this.finish();
    }
}
