package com.olituc.designshot.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.olituc.designshot.db.DesignShotSQLiteOpenHelper;
import com.olituc.designshot.domain.MySpotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/27/18.
 * All Rights Reserved by olituc
 */

public class SpotInfoDao {
    private final DesignShotSQLiteOpenHelper mDesignShotSQLiteOpenHelper;
    private List<MySpotInfo> mMySpotInfos = new ArrayList<>();
    private MySpotInfo mMySpotInfo;
    public SpotInfoDao(Context context) {
        mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
    }

    /**
     * 新建机位信息
     */
    public boolean addNewSpot(MySpotInfo mySpotInfo){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            db.execSQL("insert into SpotInfo(spotLocation,spotCity,spotRemark,spotPic,userId,isPublic)values(?,?,?,?,?,?);",
                    new Object[]{mySpotInfo.getSpotLocation(),mySpotInfo.getSpotCity(),mySpotInfo.getSpotRemark(),
                            mySpotInfo.getSpotPic(),mySpotInfo.getUserId(),mySpotInfo.isPublic()});
        }catch (Exception e){
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    }


    /**
     * 删除机位信息
     */
    public boolean DeleteSpotInfo(int spotId){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
                db.execSQL("delete from SpotInfo where spotId = ?",new Object[]{spotId});
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    }
    /**
     * 搜索所有机位信息
     */
    public List<MySpotInfo> query(){
        mMySpotInfos.clear();
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SpotInfo", null);
        try {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mMySpotInfo = new MySpotInfo();
                mMySpotInfo.setSpotId(Integer.parseInt(cursor.getString(0)));
                mMySpotInfo.setSpotLocation(cursor.getString(1));
                mMySpotInfo.setSpotCity(cursor.getString(2));
                mMySpotInfo.setSpotRemark(cursor.getString(3));
                byte[] spotPics = cursor.getBlob(cursor.getColumnIndex("spotPic"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(spotPics, 0, spotPics.length);
                mMySpotInfo.setSpotPic(bitmap);
                //mMySpotInfos.get(i).setSpotPic(new Picture(cursor.getString(4)));
                mMySpotInfo.setPublic(Boolean.valueOf(cursor.getString(5)));
                mMySpotInfo.setUserId(Integer.parseInt(cursor.getString(6)));
                mMySpotInfos.add(mMySpotInfo);
            }
        } catch (Exception e) {
            cursor.close();
            db.close();
            e.printStackTrace();
        }
        cursor.close();
        db.close();
        return mMySpotInfos;
    }
}
