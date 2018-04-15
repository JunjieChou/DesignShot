package com.olituc.designshot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.olituc.designshot.Utils.URLUtils;
import com.olituc.designshot.db.dao.CityPickerDao;
import com.olituc.designshot.db.dao.WeatherInfoDao;
import com.olituc.designshot.domain.WeatherInfo;
import com.zaaach.citypicker.CityPickerActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

//根据天气情况选择图片
//根据对应城市的中文名称获取英文名称

public class WeatherInfoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CITY = 0;
    private static final int LOAD_DATA_SUCCESS = 1;
    private static final int LOAD_DATA_FAILED = 2;
    private static final int CREATE_NEW_STATUS = 3;
    private TextView mTv_weather_currentStatus;
    private ImageView mIv_weather_icon;
    private TextView mTv_weather_city;//设置city_EN选择的中文名称
    private TextView mTv_weather_date;
    private TextView mTv_today_temperature;
    private TextView mTv_today_air_quality;
    private TextView mTv_today_visible_degree;
    private TextView mTv_today_sunrise_sunset;
    private TextView mTv_today_blue_hour;
    private TextView mToday_weather_degree;
    private TextView mTomorrow_weather_degree;
    private TextView mAfterTomorrow_weather_degree;
    private ImageView mToday_weather_icon;
    private ImageView mTomorrow_weather_icon;
    private ImageView mAfterTomorrow_weather_icon;
    private TextView mToday_weather_date;
    private TextView mTomorrow_weather_date;
    private TextView mAfterTomorrow_weather_date;
    private LinearLayout mLl_main_weather_bg;

    private List<WeatherInfo> mWeatherInfoList = new ArrayList<>();
    private String city_EN = "suzhou"; //默认城市设置为苏州
    private String city_CN = "苏州";//默认城市设置为苏州，与city_EN不同的是city_CN可以有县级市
    private int currentFocus = 1;//获取当前焦点

    private WeatherInfoDao mWeatherInfoDao = new WeatherInfoDao(this);

    /**
     * getHandler用于实现网络访问后的接口，获取到网络访问的json对象
     * 并且将对象解析到数据中
     */
    private Handler getHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_DATA_SUCCESS:
                    JSONObject weatherJSON= (JSONObject) msg.obj;
                    Log.e("json ======",weatherJSON.toString());
                    mWeatherInfoList = parseWeatherJson(weatherJSON);
                    try {
                        mWeatherInfoDao.clearTable();
                        mWeatherInfoDao.addWeather(mWeatherInfoList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setDatatoUI(mWeatherInfoList);
                    break;
                case  LOAD_DATA_FAILED:
                    Toast.makeText(getApplicationContext(),msg.obj.toString(), Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        initialization();//初始化
        getWeatherData();
    }

    /**
     * 获取天气信息
     * 1.获取文件判断是否存在
     * 2.文件存在提取城市英文名和中文名和日期，设置中文名到UI，比对日期，与当前日期是否相符
     * 3.日期相符，从数据库中提取数据
     * 4.日期不符，网络请求返回从当前日期开始的json格式数据并且解析json格式并写入到数据库
     * 5.文件不存在，网络请求返回从当前日期开始的对应城市的三天天气的json数据
     */
    private void getWeatherData() {
        File file = new File(this.getFilesDir(), "weatherCity.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String info = br.readLine();
                city_EN = info.split("##")[0];
                String date = info.split("##")[1];
                city_CN = info.split("##")[2];
                Log.i("city",city_CN);
                Log.i("date",date);
                Log.i("city",city_EN);
                mTv_weather_city.setText(city_CN);
                if (date.equals(new java.sql.Date(new Date().getTime()).toString())) {
                    mWeatherInfoList = getWeatherFromWeatherDB();
                    setDatatoUI(mWeatherInfoList);//将解析好的数据设置到数据库当中
                } else {
                    sendGetRequestForWeather(city_EN);//根据城市名称获取到信息
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sendGetRequestForWeather(city_EN);//根据城市名称获取到信息
        }
    }

    /**
     * 根据sendGetRequestForWeather()返回的jsonobject
     * 对象，解析json数据，返回一个LIST<WeatherInfo>
     * @param weatherJson
     * @return
     */
    private List<WeatherInfo> parseWeatherJson(JSONObject weatherJson) {
        try {
            mWeatherInfoList.clear();
            for (int i = 1 ; i < 4 ; ++i){
                String jname = "day"+i;
                JSONObject weather = (JSONObject)weatherJson.get(jname);
                int weatherId = weather.getIntValue("weatherId");
                int weatherTemp = weather.getIntValue("weatherTemp");
                String weatherCondition = weather.getString("weatherCondition");
                String weatherAirQua = weather.getString("weatherAirQua");
                String weatherAirVis = weather.getString("weatherAirVis");
                //获取时间，由于json格式数据中的时间显示是以时间戳显示的，所以需要转化为date以及time类型
                java.sql.Date weatherDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd")
                        .parse(new SimpleDateFormat("yyyy-MM-dd").format(weather.getDate("weatherDate"))).getTime());
                Time weatherSunRise = new Time(new SimpleDateFormat("HH:mm").parse(new SimpleDateFormat("HH:mm").format(weather.getDate("weatherSunRise"))).getTime());
                Time weatherSunSet = new Time(new SimpleDateFormat("HH:mm").parse(new SimpleDateFormat("HH:mm").format(weather.getDate("weatherSunSet"))).getTime());
                Time weatherBlueHour = new Time(new SimpleDateFormat("HH:mm").parse(new SimpleDateFormat("HH:mm").format(weather.getDate("weatherBlueHour"))).getTime());
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setWeatherId(weatherId);
                weatherInfo.setWeatherTemp(weatherTemp);
                weatherInfo.setWeatherDate(weatherDate);
                weatherInfo.setWeatherCondition(weatherCondition);
                weatherInfo.setWeatherAirQua(weatherAirQua);
                weatherInfo.setWeatherAirVis(weatherAirVis);
                weatherInfo.setWeatherSunRise(weatherSunRise);
                weatherInfo.setWeatherSunSet(weatherSunSet);
                weatherInfo.setWeatherBlueHour(weatherBlueHour);
                mWeatherInfoList.add(weatherInfo);
                Log.e("size",mWeatherInfoList.size()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mWeatherInfoList;
    }

    /**
     * 获取天气链表，并且将链表设置到UI界面中
     *
     * @param weatherInfos
     */
    private void setDatatoUI(List<WeatherInfo> weatherInfos) {
        setMainDatabyCurrentFocus(weatherInfos.get(currentFocus-1));
        mToday_weather_degree.setText(weatherInfos.get(0).getWeatherTemp() + "℃");
        mToday_weather_date.setText(showWeekByDate(weatherInfos.get(0).getWeatherDate()));
        mTomorrow_weather_degree.setText(weatherInfos.get(1).getWeatherTemp() + "℃");
        mTomorrow_weather_date.setText(showWeekByDate(weatherInfos.get(1).getWeatherDate()));
        mAfterTomorrow_weather_degree.setText(weatherInfos.get(2).getWeatherTemp() + "℃");
        mAfterTomorrow_weather_date.setText(showWeekByDate(weatherInfos.get(2).getWeatherDate()));
    }

    /**
     * 根据日期返回那天是星期几的字符串
     * @param weatherDate
     * @return
     */
    private String showWeekByDate(java.sql.Date weatherDate) {
        Date date = new Date(weatherDate.getTime());
        String weekday = new SimpleDateFormat("EEEE").format(date);
        String result = "星期一";
        switch (weekday) {
            case "Monday":
                result = "星期一";
                break;
            case "Tuesday":
                result = "星期二";
                break;
            case "Wednesday":
                result = "星期三";
                break;
            case "Thursday":
                result = "星期四";
                break;
            case "Friday":
                result = "星期五";
                break;
            case "Saturday":
                result = "星期六";
                break;
            case "Sunday":
                result = "星期日";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 设置天气界面主要的控件的显示文字
     *  @param weatherInfos
     * @param
     */
    private void setMainDatabyCurrentFocus(WeatherInfo weatherInfos) {
        mTv_weather_date.setText(weatherInfos.getWeatherDate().toString());
        mTv_today_temperature.setText(weatherInfos.getWeatherTemp() + "°");
        mTv_today_air_quality.setText("空气质量" + weatherInfos.getWeatherAirQua());
        mTv_today_visible_degree.setText("可见度：" + weatherInfos.getWeatherAirVis());
        mTv_today_sunrise_sunset.setText("日出：" + weatherInfos.getWeatherSunRise() + "   日落：" + weatherInfos.getWeatherSunSet());
        mTv_today_blue_hour.setText("蓝色时间：" + weatherInfos.getWeatherBlueHour());
        mTv_weather_currentStatus.setText(weatherInfos.getWeatherCondition());
    }

    /**
     * 根据城市名称从网络接口中获取一个json格式的数据
     * 并且通过handler机制，将互殴到的json对象以handler的形式发送到主线程
     *！！注意：asynchttpclient是异步访问需要注意使用handler，
     *         在访问结束后再在主线程中进行数据的解析
     * @param city_en
     * @return jsonObject
     */
    private void sendGetRequestForWeather(String city_en) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("city_EN",city_en);
            String weatherURL = URLUtils.getURL(this)+"/getWeather";//通过配置文件获取url
            client.post(weatherURL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Message msg = Message.obtain();
                    try {
                        JSONObject jsonWeather = JSONObject.parseObject(new String(responseBody));
                        msg.what = LOAD_DATA_SUCCESS;
                        msg.obj = jsonWeather;
                    } catch (Exception e) {
                        e.printStackTrace();
                        msg.what = LOAD_DATA_FAILED;
                        msg.obj = "数据获取失败";
                    }
                    getHandler.sendMessage(msg);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Message msg = Message.obtain();
                    msg.what = LOAD_DATA_FAILED;
                    msg.obj = "链接服务器失败"+error.toString();
                    getHandler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取天气信息并且将三天的信息以List<WeatherInfo>的形式返回
     *
     * @return
     */
    private List<WeatherInfo> getWeatherFromWeatherDB() {
        List<WeatherInfo> query = mWeatherInfoDao.query();
        return query;
    }

    /**
     * 初始化控件，找到控件
     */
    private void initialization() {
        mTv_weather_currentStatus = findViewById(R.id.tv_weather_currentStatus);
        mIv_weather_icon = findViewById(R.id.iv_weather_icon);
        mTv_weather_city = findViewById(R.id.tv_weather_city);
        mTv_weather_date = findViewById(R.id.tv_weather_date);
        mTv_today_temperature = findViewById(R.id.tv_today_temperature);
        mTv_today_air_quality = findViewById(R.id.tv_today_air_quality);
        mTv_today_visible_degree = findViewById(R.id.tv_today_visible_degree);
        mTv_today_sunrise_sunset = findViewById(R.id.tv_today_sunrise_sunset);
        mTv_today_blue_hour = findViewById(R.id.tv_today_Blue_hour);
        mToday_weather_degree = findViewById(R.id.today_weather_degree);
        mToday_weather_icon = findViewById(R.id.today_weather_icon);
        mToday_weather_date = findViewById(R.id.today_weather_date);
        mTomorrow_weather_degree = findViewById(R.id.tomorrow_weather_degree);
        mTomorrow_weather_icon = findViewById(R.id.tomorrow_weather_icon);
        mTomorrow_weather_date = findViewById(R.id.tomorrow_weather_date);
        mAfterTomorrow_weather_degree = findViewById(R.id.the_day_after_tomorrow_weather_degree);
        mAfterTomorrow_weather_icon = findViewById(R.id.the_day_after_tomorrow_weather_icon);
        mAfterTomorrow_weather_date = findViewById(R.id.the_day_after_tomorrow_weather_date);
        mLl_main_weather_bg = findViewById(R.id.main_weather_bg);
    }

    /**
     * 调用onDestroy()方法
     *
     * @param view
     */
    public void retractWeatherActivity(View view) {
        this.finish();
    }

    /**
     * 创建计划根据currentFocus跳转到createPlanActivity；
     * @param view
     */
    public void createNewPlanByWeather(View view) {
        String dueDate = mWeatherInfoList.get(currentFocus - 1).getWeatherDate().toString();
        Bundle bundle = new Bundle();
        bundle.putString("dueDate",dueDate);
        startActivityForResult(new Intent(this,CreateNewPlanActivity.class).putExtras(bundle),CREATE_NEW_STATUS);
    }


    /**
     * 添加按钮点击事件
     * 分别是三个打开按键
     * @param view
     */
    public void todayWeatherClick(View view){
        currentFocus = 1;
        mLl_main_weather_bg.setBackgroundColor(getResources().getColor(R.color.today_weather_background));
        setMainDatabyCurrentFocus(mWeatherInfoList.get(0));
    }
    public void tomorrowWeatherClick(View view){
        currentFocus = 2;
        mLl_main_weather_bg.setBackgroundColor(getResources().getColor(R.color.tomorrow_weather_background));
        setMainDatabyCurrentFocus(mWeatherInfoList.get(1));
    }
    public void afterTomorrowWeatherClick(View view){
        currentFocus = 3;
        mLl_main_weather_bg.setBackgroundColor(getResources().getColor(R.color.the_day_after_tomorrow_weather_background));
        setMainDatabyCurrentFocus(mWeatherInfoList.get(2));
    }

    /**
     * 打开选择界面
     *
     * @param view
     */
    public void selectCity(View view) {
        startActivityForResult(new Intent(WeatherInfoActivity.this, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
    }


    /**
     * 重写onActivityResult，用于获取cityPicker返回的值
     * 并且将值设置到星期几的天数上
     * 获取cityPicker选择的城市的返回值，通过utils类将中文名称转化为英文名称
     * 通过调用获取天气方法，操作handler进而执行对于界面的操作
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                mTv_weather_city.setText(city);
                city_CN = city.substring(0,2);
                CityPickerDao dao = new CityPickerDao(this);
                city_EN = dao.getCityEN(city_CN);
                sendGetRequestForWeather(city_EN);
                Toast.makeText(this, "当前城市："+city_CN, Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == CREATE_NEW_STATUS){
            switch (resultCode) {
                case 1:
                    Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this,"未添加", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 重写onDestroy()方法
     * 将现在的日期和当前的city_EN以及city_CN写入到文件中
     * 文件名称为“weatherCity.txt”
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            File file = new File(this.getFilesDir(), "weatherCity.txt");
            FileOutputStream fos = new FileOutputStream(file);
            java.sql.Date date = new java.sql.Date(new Date().getTime());
            String info = city_EN + "##" + date.toString()+"##"+city_CN;
            fos.write(info.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
