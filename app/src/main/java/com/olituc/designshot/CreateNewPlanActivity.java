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
import android.widget.Toast;

import com.olituc.designshot.Utils.SwitchButton;
import com.olituc.designshot.db.dao.PlanInfoDao;
import com.olituc.designshot.domain.MyPlanInfo;

public class CreateNewPlanActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 1;
    private EditText et_planDateTime;
    private EditText et_planLocation;
    private EditText et_planTheme;
    private EditText et_planTogether;
    private EditText et_planRemark;
    private ImageView iv_planPic;
    private SwitchButton switch_create_plan_button;
    private MyPlanInfo mMyPlanInfo = new MyPlanInfo();
    private AlertDialog.Builder mBuilder;
    private PlanInfoDao mPlanInfoDao = new PlanInfoDao(this);

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
        Bundle extras = getIntent().getExtras();
        String dueDate = extras.getString("dueDate");
        if (!"".equals(dueDate)){
            et_planDateTime.setText(dueDate);
        }
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

        //根据不同的情况设置不同的builder
        mBuilder = new AlertDialog.Builder(this);
        //日期 地址 主题不能为空
        if ("".equals(planDateTime) || "".equals(planLocation) || "".equals(planTheme)) {
            mBuilder.setMessage("时间地点主题不能为空哦～");
            mBuilder.setPositiveButton("退出编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewPlanActivity.this.finish();
                    setResult(2);
                }
            });
            mBuilder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            mBuilder.show();
        }
        //确定不为空，进行创建或者取消操作
        else {
            mBuilder.setMessage("确认创建？");
            mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                    //返回这个对象给dao进行操作
                    boolean result = mPlanInfoDao.addNewPlan(mMyPlanInfo);
                    if(result == true){
                        setResult(1);
                    }else {
                        setResult(2);
                    }
                    CreateNewPlanActivity.this.finish();
                }
            });
            mBuilder.setNegativeButton("取消",null);
            mBuilder.setNeutralButton("取消并且退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewPlanActivity.this.finish();
                    setResult(2);
                }
            });
            mBuilder.show();
        }
    }

    /**
     * 按钮事件，进行图片的跳转选择
     * 注意API21开始的相关权限
     * @param view
     */
    public void addANewPlanPic(View view){
        performCodeWithPermission("选择图片", new PermissionCallback() {
            @Override
            public void hasPermission() {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),IMAGE_REQUEST_CODE);
                }else {
                    Toast.makeText(CreateNewPlanActivity.this,"卡被拔出或者不可用", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 接口回调，通过选择的结果进行返回
     * @param requestCode
     * @param resultCode
     * @param data
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

}
