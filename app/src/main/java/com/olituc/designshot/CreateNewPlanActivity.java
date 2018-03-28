package com.olituc.designshot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.olituc.designshot.domain.MyPlanInfo;

public class CreateNewPlanActivity extends AppCompatActivity {

    private EditText et_planDateTime;
    private EditText et_planLocation;
    private EditText et_planTheme;
    private EditText et_planTogether;
    private EditText et_planRemark;
    private ImageView iv_planPic;
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
        iv_planPic = findViewById(R.id.iv_create_plan_image);
    }

    /**
     * 创建新计划
     * 进行判断
     * 写入本地数据库
     * 在线程中对url进行访问
     */
    public void finishCreateNewPlan(View view){

        String planDateTime = et_planDateTime.getText().toString();
        String planLocation = et_planLocation.getText().toString();
        String planTheme = et_planTheme.getText().toString();
        String planTogether = et_planTogether.getText().toString();
        String planRemark = et_planRemark.getText().toString();

        if ("".equals(planDateTime) || "".equals(planLocation) || "".equals(planTheme)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            mMyPlanInfo.setPlanTime(planDateTime);
            mMyPlanInfo.setPlanLocation(planLocation);
            mMyPlanInfo.setPlanTheme(planTheme);
            mMyPlanInfo.setPlanTogether(planTogether);
            mMyPlanInfo.setPlanRemark(planRemark);
            //获取到imageView里的bitmap
            mMyPlanInfo.setPlanPic(((BitmapDrawable)iv_planPic.getDrawable()).getBitmap());
            this.finish();
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
