package com.olituc.designshot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.olituc.designshot.bean.CreateAccountJSON;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mEt_userName;
    private EditText mEt_email;
    private EditText mEt_passWord;
    private EditText mEt_passWordAgain;
    private RadioButton mCb_acceptPolicy;
    private boolean canRegistered = false;
    private TextView mTv_judgePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initializeComponents();
    }

    private void initializeComponents() {
        mEt_userName = findViewById(R.id.userName);
        mEt_email = findViewById(R.id.registerEmailAddress);
        mEt_passWord = findViewById(R.id.registerPassWord);
        mEt_passWordAgain = findViewById(R.id.registerPassWordagain);
        mCb_acceptPolicy = findViewById(R.id.cb_accept_privacy);
        mTv_judgePass = findViewById(R.id.judgePass);

        mEt_passWordAgain.addTextChangedListener(new PassCheckListener());
    }

    public void createAccount(View view){
        if(canRegistered){
            String userName = mEt_userName.getText().toString().trim();
            String Email = mEt_email.getText().toString().trim();
            String passWord = mEt_passWord.getText().toString().trim();
            try {
                String url = "http://10.16.17.103:8080/register";
                //打开浏览器
                AsyncHttpClient client = new AsyncHttpClient();
                //设置请求参数
                RequestParams params = new RequestParams();
                params.put("userName",userName);
                params.put("Email",Email);
                params.put("passWord",passWord);
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Gson gson = new Gson();
                        CreateAccountJSON createAccountJSON = gson.fromJson(new JsonReader(new InputStreamReader(new ByteArrayInputStream(responseBody))), CreateAccountJSON.class);
                        if (createAccountJSON.getStatus()!=null) {
                            Toast.makeText(getApplicationContext(), createAccountJSON.getStatus(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this,"现在还不能注册哦", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelCreateAccount(View view){
        this.finish();
    }



    //检测密码接口
    private class PassCheckListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String passWord = mEt_passWord.getText().toString().trim();
            String checkPass = mEt_passWordAgain.getText().toString().trim();
            if ("".equals(checkPass)){
                mTv_judgePass.setText("×");
                mTv_judgePass.setTextColor(Color.RED);
                canRegistered = false;
            }else {
                if(passWord.equals(checkPass)){
                    mTv_judgePass.setText("√");
                    mTv_judgePass.setTextColor(Color.parseColor("#ff4081"));
                    canRegistered = true;
                }else {
                    mTv_judgePass.setText("×");
                    mTv_judgePass.setTextColor(Color.RED);
                    canRegistered = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}


















