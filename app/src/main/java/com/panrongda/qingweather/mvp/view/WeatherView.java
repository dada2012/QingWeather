package com.panrongda.qingweather.mvp.view;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

/**
 * Create by Dada on 2019/12/17 6:49.
 */
public interface WeatherView {

    void onWeatherRequestStart();

    void onWeatherFinished();

    void onWeatherLoad(Weather weather);

    void onWeatherFailed(Code code);

    void onWeatherError(Throwable e);
}
