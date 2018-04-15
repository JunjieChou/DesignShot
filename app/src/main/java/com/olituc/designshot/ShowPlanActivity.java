package com.olituc.designshot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.db.dao.PlanInfoDao;

public class ShowPlanActivity extends AppCompatActivity {

    private ImageView iv_show_plan_image;
    private TextView tv_show_plan_title;
    private TextView tv_show_plan_time;
    private TextView tv_show_plan_theme;
    private int mPlanId;
    private PlanInfoDao mPlanInfoDao;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"删除失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ShowPlanActivity.this.finish();
                    break;
                case 1:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ShowPlanActivity.this.finish();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);
        Bundle extras = this.getIntent().getExtras();
        initializeViews(extras);
    }

    /**
     * 初始化activity，同时传入Bundle对象进行解析，并且展现
     * @param extras
     */
    private void initializeViews(Bundle extras) {

        iv_show_plan_image = findViewById(R.id.iv_show_plan_image);
        tv_show_plan_theme = findViewById(R.id.tv_show_plan_theme);
        tv_show_plan_time = findViewById(R.id.tv_show_plan_time);
        tv_show_plan_title = findViewById(R.id.tv_show_plan_title);

        mPlanId = extras.getInt("planId");
        String planLocation = extras.getString("planLocation");
        String planTime = extras.getString("planTime");
        String planTheme = extras.getString("planTheme");
        byte[] planPicBytes = extras.getByteArray("planPicByte");
        Bitmap planPic = BitmapFactory.decodeByteArray(planPicBytes,0,planPicBytes.length);

        iv_show_plan_image.setImageBitmap(planPic);
        tv_show_plan_title.setText(planLocation);
        tv_show_plan_time.setText(planTime);
        tv_show_plan_theme.setText(planTheme);
    }

    public void ClosePlanShowClick(View view){
        this.finish();
    }

    public void DeletePlanShowClick(View view) {
        mPlanInfoDao = new PlanInfoDao(this);
        new Thread(){
            @Override
            public void run() {
                boolean result = mPlanInfoDao.deletePlan(mPlanId);
                Message msg = Message.obtain();
                if(result){
                    msg.what = 1;
                }else {
                    msg.what = 0;
                }
                mHandler.sendMessage(msg);
            }
        }.start();
    }
}
