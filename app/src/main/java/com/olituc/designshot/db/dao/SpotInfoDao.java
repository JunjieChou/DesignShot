package com.olituc.designshot.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.olituc.designshot.db.DesignShotSQLiteOpenHelper;
import com.olituc.designshot.domain.MySpotInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/27/18.
 * All Rights Reserved by olituc
 */

public class SpotInfoDao {
    private final DesignShotSQLiteOpenHelper mDesignShotSQLiteOpenHelper;
    private List<MySpotInfo> mMySpotInfos = new ArrayList<>();
    public SpotInfoDao(Context context) {
        mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
    }

    /**
     * 新建机位信息
     */
    public boolean addNewSpot(String spotLocation, String spotCity, String spotRemark, ByteArrayOutputStream spotPic, int userId, boolean isPublic){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        try {
            db.execSQL("insert into SpotInfo(spotLocation,spotCity,spotRemark,spotPic,userId,isPublic)values(?,?,?,?,?,?);",
                    new Object[]{spotLocation,spotCity,spotRemark,spotPic.toByteArray(),userId,isPublic});
        }catch (Exception e){
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
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SpotInfo", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            mMySpotInfos.get(i).setSpotId(Integer.parseInt(cursor.getString(0)));
            mMySpotInfos.get(i).setSpotLocation(cursor.getString(1));
            mMySpotInfos.get(i).setSpotCity(cursor.getString(2));
            mMySpotInfos.get(i).setSpotRemark(cursor.getString(3));
            byte[] spotPics = cursor.getBlob(cursor.getColumnIndex("spotPic"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(spotPics, 0, spotPics.length);
            mMySpotInfos.get(i).setSpotPic(bitmap);
            //mMySpotInfos.get(i).setSpotPic(new Picture(cursor.getString(4)));
            mMySpotInfos.get(i).setPublic(Boolean.valueOf(cursor.getString(5)));
            mMySpotInfos.get(i).setUserId(Integer.parseInt(cursor.getString(6)));
        }
        return mMySpotInfos;
    }
}
