package com.olituc.designshot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olituc on 4/15/18.
 * All Rights Reserved by olituc
 */
public class CityPickerHelper extends SQLiteOpenHelper {
    public CityPickerHelper(Context context) {
        super(context, "china_cities.db",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
