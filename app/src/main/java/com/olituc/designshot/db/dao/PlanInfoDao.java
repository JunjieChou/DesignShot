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
     private MyPlanInfo mMyPlanInfo;
     public PlanInfoDao(Context context){
         mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
     }

    /**
     * 新建计划信息
     */
    public boolean addNewPlan(MyPlanInfo myPlanInfo){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            db.execSQL("insert into PlanInfo(planDateTime,planLocation,planCIty,planTheme,planTogether,planRemark,planPic,userId,isPublic) values(?,?,?,?,?,?,?,?,?)",new Object[]{
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
     * 删除计划
     */
    public boolean deletePlan(int planId){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            db.execSQL("delete from PlanInfo where planId = ?",new Object[]{planId});
        }catch (Exception e){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    /**
     * 查询计划信息
     */
    public List<MyPlanInfo> query(){
        mMyPlanInfos.clear();
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PlanInfo", null);
        try {
            for (int i = 0; i <cursor.getCount() ; i++) {
                cursor.moveToPosition(i);
                mMyPlanInfo = new MyPlanInfo();
                mMyPlanInfo.setPlanId(cursor.getInt(0));
                mMyPlanInfo.setPlanTime(cursor.getString(1));
                mMyPlanInfo.setPlanLocation(cursor.getString(2));
                mMyPlanInfo.setPlanCity(cursor.getString(3));
                mMyPlanInfo.setPlanTheme(cursor.getString(4));
                mMyPlanInfo.setPlanTogether(cursor.getString(5));
                mMyPlanInfo.setPlanRemark(cursor.getString(6));
                byte[] planPics = cursor.getBlob(cursor.getColumnIndex("planPic"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(planPics, 0, planPics.length);
                mMyPlanInfo.setPlanPic(bitmap);
                mMyPlanInfo.setUserId(cursor.getInt(8));
                mMyPlanInfo.setPublic(Boolean.valueOf(cursor.getString(9)));
                mMyPlanInfos.add(mMyPlanInfo);
            }
        } catch (Exception e) {
            cursor.close();
            db.close();
            e.printStackTrace();
        }
        cursor.close();
        db.close();
        return mMyPlanInfos;
    }
}
