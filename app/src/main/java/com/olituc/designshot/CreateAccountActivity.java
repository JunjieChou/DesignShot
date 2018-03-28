package com.olituc.designshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view){
        Toast.makeText(getApplicationContext(),"ok", Toast.LENGTH_SHORT).show();
    }

    public void cancelCreateAccount(View view){
        this.finish();
    }
}
