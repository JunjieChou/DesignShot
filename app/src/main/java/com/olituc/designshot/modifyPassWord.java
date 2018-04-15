package com.olituc.designshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class modifyPassWord extends AppCompatActivity {

    private EditText mEt_forget_username;
    private EditText mEt_forget_email;
    private RadioButton mRb_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass_word);
        initialization();
    }

    private void initialization() {
        mEt_forget_username = findViewById(R.id.et_forget_username);
        mEt_forget_email = findViewById(R.id.et_forget_email);
        mRb_privacy = findViewById(R.id.cb_accept_privacy_modify);
    }

    public void ensureModifyPassWord(View view){
        /**
         * 提交服务器数据根据返回值判断是否修改成功
         */
        boolean checked = mRb_privacy.isChecked();
        if(checked) {
            String email = mEt_forget_email.getText().toString().trim();
            try {
                String url = "http://192.168.1.105:8080/findPassWord";

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("email", email);
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        mEt_forget_username.setText("");
                        mEt_forget_email.setText("");
                        Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"必须要同意隐私条约", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelModifyPassWord(View view){
        this.finish();
    }
}
