package com.olituc.designshot.Utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by olituc on 4/15/18.
 * All Rights Reserved by olituc
 */
public class CityPickUtils {
    private static Properties mProperties = new Properties();
    public static String getCityEN(String city_CN, Context context){
        String city_EN="";
        try {
            InputStream is = context.getAssets().open("CityConstract.properties");
            mProperties.load(is);
            System.out.println(city_CN);
            city_EN = mProperties.getProperty("\"city_CN\"");
            System.out.println(city_EN);
        }catch (Exception e){}
        return city_EN;
    }
}
