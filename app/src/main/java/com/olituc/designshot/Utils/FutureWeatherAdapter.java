package com.olituc.designshot.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.olituc.designshot.R;

/**
 * Created by olituc on 3/10/18.
 * All Rights Reserved by olituc
 */

public class FutureWeatherAdapter extends BaseAdapter{

    private static final int FIXED_ITEM_COUNT = 4;

    @Override
    public int getCount() {
        return FIXED_ITEM_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.weather_info_layout,null);
        TextView tv_weather_info_date = (TextView) view.findViewById(R.id.tv_weather_info_date);
        ImageView iv_weather_info_status_image = (ImageView) view.findViewById(R.id.iv_weather_info_status_image);
        TextView tv_weather_info_temperature = (TextView) view.findViewById(R.id.tv_weather_info_temperature);
        tv_weather_info_date.setText("星期四");
        iv_weather_info_status_image.setImageResource(R.drawable.cloudy);
        tv_weather_info_temperature.setText("20°");
        return view;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
