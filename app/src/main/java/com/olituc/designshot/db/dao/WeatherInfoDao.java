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
     * 参数为List类型的WeatherInfo
     * 如果修改数据成功，修改布尔值
     * @param list
     */
    public boolean addWeather(List<WeatherInfo> list){
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
        boolean result;
        try {
            for (int i = 0; i < list.size(); i++) {
                db.execSQL("insert into WeatherInfo(weatherId,weatherDate," +
                                "weatherTemp,weatherCondition,weatherAirQua,weatherAirVis," +
                                "weatherSunRise,weatherSunSet,weatherBlueHour) values(?,?,?,?,?,?,?,?,?);",
                        new Object[]{
                                list.get(i).getWeatherId(),
                                list.get(i).getWeatherDate(),
                                list.get(i).getWeatherTemp(),
                                list.get(i).getWeatherCondition(),
                                list.get(i).getWeatherAirQua(),
                                list.get(i).getWeatherAirVis(),
                                list.get(i).getWeatherSunRise(),
                                list.get(i).getWeatherSunSet(),
                                list.get(i).getWeatherBlueHour()
                        });
            }
            db.close();
            result = true;
        } catch (Exception e){
            result = false;
        }finally {
            if(db != null) db.close();
        }
        return result;
    }

    /**
     * 清空表
     * @return
     */
    public boolean clearTable(){
        SQLiteDatabase db = null;
        boolean result = false;
        try {
            db = mDesignShotSQLiteOpenHelper.getWritableDatabase();
            db.execSQL("delete from WeatherInfo;");
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        finally {
            if(db!=null){
                db.close();
            }
        }
        return result;
    }

    /**
     * 返回天气链表
     * @return mWeatherInfos
     */
    public List<WeatherInfo> query(){
        mWeatherInfos.clear();
        SQLiteDatabase db = mDesignShotSQLiteOpenHelper.getReadableDatabase();
        for(int i = 1 ; i< 4; ++i){
            Cursor cursor = db.rawQuery("select weatherDate,weatherTemp,weatherCondition," +
                    "weatherAirQua,weatherAirVis,weatherSunRise,weatherSunSet,weatherBlueHour" +
                    " from WeatherInfo where weatherId = ?;",new String[]{String.valueOf(i)});
            while (cursor.moveToNext()){
                try {
                    WeatherInfo weatherInfo = new WeatherInfo();
                    weatherInfo.setWeatherId(i);
                    weatherInfo.setWeatherDate(Date.valueOf(cursor.getString(0)));
                    weatherInfo.setWeatherTemp(cursor.getInt(1));
                    weatherInfo.setWeatherCondition(cursor.getString(2));
                    weatherInfo.setWeatherAirQua(cursor.getString(3));
                    weatherInfo.setWeatherAirVis(cursor.getString(4));
                    weatherInfo.setWeatherSunRise(Time.valueOf(cursor.getString(5)));
                    weatherInfo.setWeatherSunSet(Time.valueOf(cursor.getString(6)));
                    weatherInfo.setWeatherBlueHour(Time.valueOf(cursor.getString(7)));
                    mWeatherInfos.add(weatherInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(cursor!=null){
                cursor.close();
            }
        }
        return mWeatherInfos;
    }

}
