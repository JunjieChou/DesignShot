package com.olituc.designshot.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.olituc.designshot.db.CityPickerHelper;

/**
 * Created by olituc on 4/15/18.
 * All Rights Reserved by olituc
 */
public class CityPickerDao {
    private final CityPickerHelper mCityPickerHelper;
    public CityPickerDao(Context context){
        mCityPickerHelper = new CityPickerHelper(context);
    }
    public String getCityEN(String city_CN){
        String city_EN=null;
        SQLiteDatabase db = mCityPickerHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select pinyin from city where name = ?", new String[]{city_CN});
            while (cursor.moveToNext()){
                city_EN = cursor.getString(0);
            }
        }catch (Exception e){}
        finally {
            if(db!=null){db.close();}
            if(cursor!=null){cursor.close();}
        }
        return city_EN;
    }
}
