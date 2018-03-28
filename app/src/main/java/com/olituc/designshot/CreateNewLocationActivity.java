package com.olituc.designshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateNewLocationActivity extends AppCompatActivity {

    private TextView mTv_finish;
    private EditText mEt_name;
    private EditText mEt_remark;
    private ImageView mIv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_location);
        initialization();
    }

    /**
     * 找到控件
     */
    private void initialization() {
        mTv_finish = findViewById(R.id.tv_finish_create_new_location);
        mEt_name = findViewById(R.id.et_create_location_name);
        mEt_remark = findViewById(R.id.et_create_location_remark);
        mIv_image = findViewById(R.id.iv_create_location_image);
    }

    /**
     * 完成编辑
     * 需要进行判断
     * 并且进行数据库的操作
     * 到时后finish也要写到线程里
     * @param view
     */
    public void finishCreateNewLocation(View view){
        this.finish();
    }


}
