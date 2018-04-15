package com.olituc.designshot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.olituc.designshot.Utils.SwitchButton;

import cz.msebera.android.httpclient.Header;

public class SettingsActivity extends AppCompatActivity {

    private SwitchButton mSw_sync;
    private TextView mTv_userName;


    private DesignShotAPP mDesignShotAPP = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialization();
    }

    /**
     * 初始化
     */
    private void initialization() {
        mSw_sync = findViewById(R.id.switch_sync_button);
        mTv_userName = findViewById(R.id.tv_current_users);
        final DesignShotAPP app = (DesignShotAPP) this.getApplicationContext();
        String userName = app.getUserName();
        if(userName==null){
            mTv_userName.setText(app.getUserEmail());
        }else {
            mTv_userName.setText(userName);
        }
        mSw_sync.setChecked(app.isSync());
        mSw_sync.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                app.setSync(isChecked);
            }
        });
    }

    /**
     * 更换密码 复用忘记密码
     * @param view
     */
    public void changeLoginPassWord(View view){
        /**
         * 与忘记密码activity中的更换密码操作相同
         */
        mDesignShotAPP = (DesignShotAPP)this.getApplicationContext();
        final String userEmail = mDesignShotAPP.getUserEmail().toString().trim();
        if("".equals(userEmail)){
            Toast.makeText(this,"您还没有登录哦！", Toast.LENGTH_SHORT).show();
        }else {
            final String url = "http://192.168.1.105:8080/modifyPassWord";
            final EditText mEditPassWord = new EditText(this);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("请输入新密码")
                    .setView(mEditPassWord)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String passWord = mEditPassWord.getText().toString().trim();
                            if (!"".equals(passWord)) {
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("newPassWord", passWord);
                                params.put("email", userEmail);
                                client.post(url, params, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        Toast.makeText(getApplicationContext(),new String(responseBody), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(getApplicationContext(),new String(responseBody), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(),"密码不能为空哦！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        }
    }

    public void cancelLoginStatus(View view){
        /**
         * 取消状态码
         */
        DesignShotAPP app = (DesignShotAPP) this.getApplicationContext();
        app.retrieve();
        mSw_sync.setChecked(false);
        mTv_userName.setText("local admin");
    }

    public void returnToMain(View view){
        this.finish();
    }
}
