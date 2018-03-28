package com.olituc.designshot.domain;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by olituc on 3/10/18.
 * All Rights Reserved by olituc
 */

public class WeatherInfo {
    private String city;
    private Date date;
    private Integer temperature;
    private String air_quality;
    private String visible_degree;
    private Time sunrise;
    private Time sunset;
    private Time BlueHour;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemprature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(String air_quality) {
        this.air_quality = air_quality;
    }

    public String getVisible_degree() {
        return visible_degree;
    }

    public void setVisible_degree(String visible_degree) {
        this.visible_degree = visible_degree;
    }

    public Time getSunrise() {
        return sunrise;
    }

    public void setSunrise(Time sunrise) {
        this.sunrise = sunrise;
    }

    public Time getSunset() {
        return sunset;
    }

    public void setSunset(Time sunset) {
        this.sunset = sunset;
    }

    public Time getBlueHour() {
        return BlueHour;
    }

    public void setBlueHour(Time blueHour) {
        BlueHour = blueHour;
    }
}
