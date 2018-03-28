package com.olituc.designshot.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.olituc.designshot.db.DesignShotSQLiteOpenHelper;
import com.olituc.designshot.domain.MyPlanInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/27/18.
 * All Rights Reserved by olituc
 */

public class PlanInfoDao {
     private final DesignShotSQLiteOpenHelper mDesignShotSQLiteOpenHelper;
     private List<MyPlanInfo> mMyPlanInfos= new ArrayList<>();
     public PlanInfoDao(Context context){
         mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
     }

    /**
     * 新建计划信息
     */
    public boolean addNewPlan(MyPlanInfo myPlanInfo){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            db.execSQL("insert into PlanInfo(planDateTime,planLocation,planCIty,planTheme,planTogether,planRemark,planPic,userId,isPublic) values(?,?,?,?,?,?,?,?,?,?)",new Object[]{
                    myPlanInfo.getPlanTime(),myPlanInfo.getPlanLocation(),myPlanInfo.getPlanCity(),
                    myPlanInfo.getPlanTheme(),myPlanInfo.getPlanTogether(),
                    myPlanInfo.getPlanRemark(),myPlanInfo.getPlanPic(),myPlanInfo.getUserId(),myPlanInfo.isPublic()});
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }

    /**
     * 查询计划信息
     */
    public List<MyPlanInfo> query(){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from PlanInfo", null);
            for (int i = 0; i <cursor.getCount() ; i++) {
                cursor.moveToPosition(i);
                mMyPlanInfos.get(i).setPlanId(cursor.getInt(0));
                mMyPlanInfos.get(i).setPlanTime(cursor.getString(1));
                mMyPlanInfos.get(i).setPlanLocation(cursor.getString(2));
                mMyPlanInfos.get(i).setPlanCity(cursor.getString(3));
                mMyPlanInfos.get(i).setPlanTheme(cursor.getString(4));
                mMyPlanInfos.get(i).setPlanTogether(cursor.getString(5));
                mMyPlanInfos.get(i).setPlanRemark(cursor.getString(6));
                byte[] planPics = cursor.getBlob(cursor.getColumnIndex("planPic"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(planPics, 0, planPics.length);
                mMyPlanInfos.get(i).setPlanPic(bitmap);
                mMyPlanInfos.get(i).setUserId(cursor.getInt(8));
                mMyPlanInfos.get(i).setPublic(Boolean.valueOf(cursor.getString(9)));
            }
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
        }
        db.close();
        return mMyPlanInfos;
    }
}
