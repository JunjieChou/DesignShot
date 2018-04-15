package com.olituc.designshot.domain;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by olituc on 3/10/18.
 * All Rights Reserved by olituc
 */

public class WeatherInfo {

    private int weatherId;
    private Date weatherDate;
    private int weatherTemp;
    private String weatherCondition;
    private String weatherAirQua;
    private String weatherAirVis;
    private Time weatherSunRise;
    private Time weatherSunSet;
    private Time weatherBlueHour;

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }

    public int getWeatherTemp() {
        return weatherTemp;
    }

    public void setWeatherTemp(int weatherTemp) {
        this.weatherTemp = weatherTemp;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherAirQua() {
        return weatherAirQua;
    }

    public void setWeatherAirQua(String weatherAirQua) {
        this.weatherAirQua = weatherAirQua;
    }

    public String getWeatherAirVis() {
        return weatherAirVis;
    }

    public void setWeatherAirVis(String weatherAirVis) {
        this.weatherAirVis = weatherAirVis;
    }

    public Time getWeatherSunRise() {
        return weatherSunRise;
    }

    public void setWeatherSunRise(Time weatherSunRise) {
        this.weatherSunRise = weatherSunRise;
    }

    public Time getWeatherSunSet() {
        return weatherSunSet;
    }

    public void setWeatherSunSet(Time weatherSunSet) {
        this.weatherSunSet = weatherSunSet;
    }

    public Time getWeatherBlueHour() {
        return weatherBlueHour;
    }

    public void setWeatherBlueHour(Time weatherBlueHour) {
        this.weatherBlueHour = weatherBlueHour;
    }
}
