package com.olituc.designshot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.olituc.designshot.Utils.SwitchButton;
import com.olituc.designshot.domain.MyPlanInfo;

public class CreateNewPlanActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 1;
    private EditText et_planDateTime;
    private EditText et_planLocation;
    private EditText et_planTheme;
    private EditText et_planTogether;
    private EditText et_planRemark;
    private ImageView iv_planPic;
    private SwitchButton switch_create_plan_button;
    private MyPlanInfo mMyPlanInfo = new MyPlanInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_plan);
        initCreatePlanView();
    }

    /**
     * 找到控件
     */
    private void initCreatePlanView() {
        et_planDateTime = findViewById(R.id.et_create_plan_time);
        et_planLocation = findViewById(R.id.et_create_plan_location);
        et_planTheme = findViewById(R.id.et_create_plan_theme);
        et_planTogether = findViewById(R.id.et_create_plan_remind);
        et_planRemark = findViewById(R.id.et_create_plan_remark);
        switch_create_plan_button = findViewById(R.id.switch_create_plan_button);
        iv_planPic = findViewById(R.id.iv_create_plan_image);
    }

    /**
     * 创建新计划
     * 进行判断
     * 写入本地数据库
     * 在线程中对url进行访问
     */
    public void finishCreateNewPlan(View view){

        final String planDateTime = et_planDateTime.getText().toString();
        final String planLocation = et_planLocation.getText().toString();
        final String planTheme = et_planTheme.getText().toString();
        final String planTogether = et_planTogether.getText().toString();
        final String planRemark = et_planRemark.getText().toString();
        final boolean isPublic = switch_create_plan_button.isChecked();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if ("".equals(planDateTime) || "".equals(planLocation) || "".equals(planTheme)) {
            builder.setMessage("时间地点主题不能为空哦～");
            builder.setPositiveButton("退出编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewPlanActivity.this.finish();
                }
            });
            builder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        } else {
            builder.setMessage("确认创建？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mMyPlanInfo.setPlanTime(planDateTime);
                    mMyPlanInfo.setPlanLocation(planLocation);
                    mMyPlanInfo.setPlanTheme(planTheme);
                    mMyPlanInfo.setPlanTogether(planTogether);
                    mMyPlanInfo.setPlanRemark(planRemark);
                    //获取到imageView里的bitmap
                    mMyPlanInfo.setPlanPic(((BitmapDrawable)iv_planPic.getDrawable()).getBitmap());
                    mMyPlanInfo.setPublic(isPublic);
                    CreateNewPlanActivity.this.finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.setNeutralButton("取消并且退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewPlanActivity.this.finish();
                }
            });
            builder.show();
        }
    }

    public void addANewPlanPic(View view){
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),IMAGE_REQUEST_CODE);
    }

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
                        System.out.print("complete");
                        iv_planPic.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 获取到文本框中的数据
     * 并且进行判断
     */
    private void getAllViews() {
        //DateTIme
        //planLocation
        String planDateTime = et_planDateTime.getText().toString();
        String planLocation = et_planLocation.getText().toString();
        String planTheme = et_planTheme.getText().toString();
        String planTogether = et_planTogether.getText().toString();
        String planRemark = et_planRemark.getText().toString();
    }
}
