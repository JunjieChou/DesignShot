package com.olituc.designshot;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.db.dao.SpotInfoDao;

public class ShowLocationActivity extends AppCompatActivity {

    private TextView tv_show_location_remark;
    private TextView tv_show_location_title;
    private ImageView iv_show_location_image;
    private int mSpotId;
    private SpotInfoDao mSpotInfoDao;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"删除失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ShowLocationActivity.this.finish();
                    break;
                case 1:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ShowLocationActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        iv_show_location_image = findViewById(R.id.iv_show_location_image);
        tv_show_location_remark = findViewById(R.id.tv_show_location_remark);
        tv_show_location_title = findViewById(R.id.tv_show_location_title);

        Bundle extras = getIntent().getExtras();
        mSpotId = extras.getInt("spotId");

        tv_show_location_title.setText(extras.getString("spotLocation"));
        tv_show_location_remark.setText(extras.getString("spotRemark"));

        byte[] spotPics = extras.getByteArray("spotPic");
        iv_show_location_image.setImageBitmap(BitmapFactory.decodeByteArray(spotPics,0,spotPics.length));

    }

    public void CloseSpotShowClick(View view){
        this.finish();
    }

    public void DeleteSpotShowClick(View view){
        mSpotInfoDao = new SpotInfoDao(this);
        new Thread(){
            @Override
            public void run() {
                boolean result = mSpotInfoDao.DeleteSpotInfo(mSpotId);
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
