package com.olituc.designshot.Utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by olituc on 4/14/18.
 * All Rights Reserved by olituc
 */
public class URLUtils {
    public static String getURL(Context context){
        String url = "";
        try {
            InputStream ras = context.getAssets().open("url.properties");
            Properties properties = new Properties();
            properties.load(ras);
            url = properties.getProperty("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
