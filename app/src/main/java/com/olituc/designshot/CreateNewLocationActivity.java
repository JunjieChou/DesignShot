package com.olituc.designshot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.Utils.SwitchButton;
import com.olituc.designshot.db.dao.SpotInfoDao;
import com.olituc.designshot.domain.MySpotInfo;

public class CreateNewLocationActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 1;
    private TextView mTv_finish;
    private EditText mEt_name;
    private EditText mEt_remark;
    private ImageView mIv_image;
    private SwitchButton mSwitch_create_spot_button;
    private AlertDialog.Builder mBuilder;
    private MySpotInfo mMySpotInfo;
    private SpotInfoDao mSpotInfoDao = new SpotInfoDao(this);

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
        mSwitch_create_spot_button = findViewById(R.id.switch_create_spot_button);
    }

    /**
     * 完成编辑
     * 需要进行判断
     * 并且进行数据库的操作
     * 到时后finish也要写到线程里
     * @param view
     */
    public void finishCreateNewLocation(View view){
        final String spotName = mEt_name.getText().toString();
        final String spotRemark = mEt_remark.getText().toString();
        final boolean isPublic = mSwitch_create_spot_button.isChecked();
        mBuilder = new AlertDialog.Builder(this);
        if("".equals(spotName)){
            mBuilder.setMessage("地点不能为空哦～");
            mBuilder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewLocationActivity.this.finish();
                }
            });
            mBuilder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            mBuilder.show();
        }else {
            mBuilder.setMessage("确认编辑吗？");
            mBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mMySpotInfo = new MySpotInfo();
                    mMySpotInfo.setSpotLocation(spotName);
                    mMySpotInfo.setSpotRemark(spotRemark);
                    mMySpotInfo.setSpotPic(((BitmapDrawable)mIv_image.getDrawable()).getBitmap());
                    mMySpotInfo.setPublic(isPublic);
                    boolean result = mSpotInfoDao.addNewSpot(mMySpotInfo);
                    if(result == true){
                        setResult(1);
                    }else {
                        setResult(2);
                    }
                    CreateNewLocationActivity.this.finish();
                }
            });
            mBuilder.setNegativeButton("取消并且退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewLocationActivity.this.finish();
                }
            });
            mBuilder.setNeutralButton("继续编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            mBuilder.show();
        }
    }

    /**
     * 添加图片
     * 注意考虑API权限
     */
    public void addANewSpotPic(View view){
        performCodeWithPermission("选择图片", new PermissionCallback() {
            @Override
            public void hasPermission() {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),IMAGE_REQUEST_CODE);
                }else {
                    Toast.makeText(CreateNewLocationActivity.this,"卡被拔出或者不可用", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 重写activityresult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mIv_image.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }
}
