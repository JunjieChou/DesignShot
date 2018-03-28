package com.olituc.designshot;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * A login screen that offers login via username/password
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void userLogin(View view){
        Toast.makeText(getApplicationContext(),"sdf", Toast.LENGTH_SHORT).show();
    }

    public void forgetPassword(View view){
        Toast.makeText(getApplicationContext(),"活该哈哈哈", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,modifyPassWord.class));
    }

    public void redirectToCreateAccount(View view){
        startActivity(new Intent(this,CreateAccountActivity.class));
    }

    public void finishTheActivity(View view){
        this.finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

