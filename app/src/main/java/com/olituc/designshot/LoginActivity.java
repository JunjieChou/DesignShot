package com.olituc.designshot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.olituc.designshot.bean.LoginCheckJSON;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A login screen that offers login via username/password
 */
public class LoginActivity extends AppCompatActivity {

    private static final int READ_SUCCESS = 1;
    private static final int READ_FAIL = 0;
    private TextView mEmail;
    private TextView mPassWord;

    private String email;
    private String password;

    private DesignShotAPP mDesignShotAPP;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case READ_SUCCESS:
                    LoginCheckJSON loginCheckJSON = (LoginCheckJSON) msg.obj;
                    Toast.makeText(LoginActivity.this,loginCheckJSON.getFeedBack(), Toast.LENGTH_SHORT).show();
                    mDesignShotAPP = (DesignShotAPP) getApplicationContext();
                    mDesignShotAPP.setUserStatus(1);
                    mDesignShotAPP.setUserEmail(loginCheckJSON.getUserEmail());
                    mDesignShotAPP.setUserName(loginCheckJSON.getUserName());
                    setResult(1);
                    finish();
                    break;
                case READ_FAIL:
                    Toast.makeText(LoginActivity.this,msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
    }

    //初始化组件
    private void initializeComponents() {
        mEmail = findViewById(R.id.email);
        mPassWord = findViewById(R.id.password);
    }

    //验证用户是否登录，返回一个值以及用户名给用户，取出第一个数字设置在TextView里，点击显示AlertDialog显示是否确认退出。
    public void userLogin(View view){
        email = mEmail.getText().toString();
        password = mPassWord.getText().toString();
        if(!"".equals(email)&&!"".equals(password)) {
            try {
                final URL url = new URL("http://192.168.1.105:8080/login?Email=" + email + "&passWord=" + password);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            InputStream in = urlConnection.getInputStream();
                            Gson gson = new Gson();
                            LoginCheckJSON loginCheckJSON = gson.fromJson(new JsonReader(new InputStreamReader(in)),LoginCheckJSON.class);
                            Message msg = Message.obtain();
                            if(loginCheckJSON.getStatus() != 0){
                                msg.what=READ_SUCCESS;
                                msg.obj = loginCheckJSON;
                            }else{
                                msg.what=READ_FAIL;
                                msg.obj = loginCheckJSON.getFeedBack();
                            }
                            mHandler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //忘记密码监听点击事件
    public void forgetPassword(View view){
        Toast.makeText(getApplicationContext(),"活该哈哈哈", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,modifyPassWord.class));
    }

    //创建账号
    public void redirectToCreateAccount(View view){
        startActivity(new Intent(this,CreateAccountActivity.class));
    }

    //返回按钮监听
    public void finishTheActivity(View view){
        this.finish();
    }
}

