package com.olituc.designshot.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.olituc.designshot.db.DesignShotSQLiteOpenHelper;
import com.olituc.designshot.domain.WeatherInfo;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/27/18.
 * All Rights Reserved by olituc
 */

public class WeatherInfoDao {
    private final DesignShotSQLiteOpenHelper mDesignShotSQLiteOpenHelper;
    private List<WeatherInfo> mWeatherInfos = new ArrayList<>();
    public WeatherInfoDao(Context context){
        mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
    }

    /**
     * 添加天气信息
     */
    public void addWeather(int weatherId, String weatherCity, Date weatherDate, int temperature, String airQuality, String airVisibility, Time sunRiseTime, Time sunSetTime,Time blueHour){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into weatherInfo(weatherId, weatherCity,weatherDate, temperature,airQuality,airVisibility, sunRiseTime,sunSetTime,blueHour) values(?,?,?,?,?,?,?,?,?) ;",
                new Object[]{weatherId,weatherCity,weatherDate,temperature,airQuality,airVisibility,sunRiseTime,sunSetTime,blueHour});
        db.close();
    }

    /**
     * 修改天气信息
     */
    public void update(int weatherId,String weatherCity,Date weatherDate, int temperature, String airQuality, String airVisibility, Time sunRiseTime, Time sunSetTime,Time blueHour){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        db.execSQL("update weatherInfo set weatherCity = ? ,weatherDate = ? ,temperature = ? ,airQuality = ? ,airVisibility = ? ,sunRiseTime = ? ,sunSetTime = ? ,blueHour = ?where weatherId = ?;",
                new Object[]{weatherCity,weatherDate,weatherCity,temperature,airQuality,airVisibility,sunRiseTime,sunSetTime,blueHour,weatherId});
        db.close();
    }

    /**
     * 查询天气
     */
    public List<WeatherInfo> query(String weatherCity){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getReadableDatabase();
        for(int i = 0 ; i< 4; ++i){
            Cursor cursor = db.rawQuery("select weatherCity,weatherDate,temperature,airQuality,airVisibility,sunRiseTime,sunSetTime from weatherInfo where weatherId = ?;", new String[]{String.valueOf(i)});
            while (cursor.moveToNext()){
                try {
                    mWeatherInfos.get(i).setCity(cursor.getString(0));
                    mWeatherInfos.get(i).setDate(Date.valueOf(cursor.getString(1)));
                    mWeatherInfos.get(i).setTemprature(cursor.getInt(2));
                    mWeatherInfos.get(i).setAir_quality(cursor.getString(3));
                    mWeatherInfos.get(i).setVisible_degree(cursor.getString(4));
                    mWeatherInfos.get(i).setSunrise(Time.valueOf(cursor.getString(5)));
                    mWeatherInfos.get(i).setSunset(Time.valueOf(cursor.getString(6)));
                    mWeatherInfos.get(i).setBlueHour(Time.valueOf(cursor.getString(7)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return mWeatherInfos;
    }

}
