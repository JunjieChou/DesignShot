package com.olituc.designshot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olituc on 3/11/18.
 * All Rights Reserved by olituc
 */

public class DesignShotSQLiteOpenHelper extends SQLiteOpenHelper {
    public DesignShotSQLiteOpenHelper(Context context) {
        super(context, "designshot", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreate数据库被创建了");
        db.execSQL("CREATE TABLE WeatherInfo " +
                "(weatherId INTEGER PRIMARY KEY ASC NOT NULL UNIQUE," +
                " weatherCity VARCHAR (20), " +
                "weatherDate DATE DEFAULT ('2019-01-01'), " +
                "temperature INTEGER DEFAULT (17), " +
                "airQuality VARCHAR (10), " +
                "airVisibility VARCHAR (10), " +
                "sunRiseTime TIME DEFAULT (6), " +
                "sunSetTime VARCHAR (20) DEFAULT (18), " +
                "blueHour VARCHAR (20));");
        db.execSQL("CREATE TABLE User " +
                "(userId INTEGER PRIMARY KEY NOT NULL, " +
                "Email VARCHAR (30) NOT NULL, " +
                "passWord VARCHAR (16) NOT NULL, " +
                "userName VARCHAR (20));");
        db.execSQL("CREATE TABLE SpotInfo (" +
                "spotId INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL, " +
                "spotLocation VARCHAR (100) NOT NULL, spotCity VARCHAR (20) NOT NULL, " +
                "spotRemark VARCHAR (2000), spotPic VARCHAR (100), " +
                "isPublic BOOLEAN NOT NULL , userId REFERENCES User (userId));");
        db.execSQL("CREATE TABLE PlanInfo " +
                "(planId INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL, " +
                "planDateTime DATETIME NOT NULL, planLocation VARCHAR (100) NOT NULL, " +
                "planCity VARCHAR (20), planTheme VARCHAR (20), " +
                "planTogether VARCHAR (100), planRemark VARCHAR (2000), " +
                "planPic VARCHAR (100), userId INTEGER REFERENCES User (userId) NOT NULL, " +
                "isPublic BOOLEAN NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpdate数据库被更新了");
    }
}
