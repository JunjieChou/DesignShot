package com.olituc.designshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.Utils.FutureWeatherAdapter;
import com.olituc.designshot.Utils.HorizontalListView;

;

public class WeatherInfoActivity extends AppCompatActivity {

    private TextView mTv_weather_currentStatus;
    private ImageView mIv_weather_icon;
    private TextView mTv_weather_city;
    private TextView mTv_weather_date;
    private TextView mTv_today_temperature;
    private TextView mTv_today_air_quality;
    private TextView mTv_today_visible_degree;
    private TextView mTv_today_sunrise_sunset;
    private TextView mTv_today_blue_hour;
    private HorizontalListView mLv_future_weather_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        innitialize();//初始化
    }

    private void innitialize() {
        mTv_weather_currentStatus = findViewById(R.id.tv_weather_currentStatus);
        mIv_weather_icon = findViewById(R.id.iv_weather_icon);
        mTv_weather_city = findViewById(R.id.tv_weather_city);
        mTv_weather_date = findViewById(R.id.tv_weather_date);
        mTv_today_temperature = findViewById(R.id.tv_today_temperature);
        mTv_today_air_quality = findViewById(R.id.tv_today_air_quality);
        mTv_today_visible_degree = findViewById(R.id.tv_today_visible_degree);
        mTv_today_sunrise_sunset = findViewById(R.id.tv_today_sunrise_sunset);
        mTv_today_blue_hour = findViewById(R.id.tv_today_Blue_hour);
        mLv_future_weather_info = findViewById(R.id.lv_future_weather_info);
        mLv_future_weather_info.setAdapter(new FutureWeatherAdapter());

        mLv_future_weather_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),position+"isclicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retractWeatherActivity(View view){
        this.finish();
    }
}
