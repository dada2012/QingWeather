package com.panrongda.qingweather.mvp.presenter;

import android.content.Context;

import com.panrongda.qingweather.base.BaseApplication;
import com.panrongda.qingweather.cache.WeatherCache;
import com.panrongda.qingweather.mvp.view.WeatherView;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * Create by Dada on 2019/12/17 6:44.
 * 天气数据请求
 */
public class WeatherPresenter {

    private static final String TAG = "WeatherPresenter";        /* Log Tag */

    private Context     ctx;
    private WeatherView weatherView;

    private WeatherCache weatherCache;

    public WeatherPresenter(WeatherView weatherView, Context context) {
        this.weatherView = weatherView;
        this.ctx = context == null ? BaseApplication.getContext() : context;
        this.weatherCache = WeatherCache.getInstance();
    }

    /**
     * 根据当前位置查询天气信息
     */
    public void getWeather() {
        weatherView.onWeatherRequestStart();
        HeWeather.getWeather(ctx, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                weatherView.onWeatherError(throwable);
                weatherView.onWeatherFinished();
            }

            @Override
            public void onSuccess(Weather weather) {
                String status = weather.getStatus();
                if (Code.OK.getCode().equalsIgnoreCase(status)) {
                    weatherView.onWeatherLoad(weather);
                    weatherCache.refresh(weather);
                } else {
                    weatherView.onWeatherFailed(Code.toEnum(status));
                }
                weatherView.onWeatherFinished();
            }
        });

    }

    /**
     * 根据城市查询天气
     *
     * @param city 城市名
     */
    public void getWeather(String city) {
        weatherView.onWeatherRequestStart();
        HeWeather.getWeather(ctx, city, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                weatherView.onWeatherError(throwable);
                weatherView.onWeatherFinished();
            }

            @Override
            public void onSuccess(Weather weather) {
                String status = weather.getStatus();
                if (Code.OK.getCode().equalsIgnoreCase(status)) {
                    weatherView.onWeatherLoad(weather);
                    weatherCache.refresh(weather);
                } else {
                    weatherView.onWeatherFailed(Code.toEnum(status));
                }
                weatherView.onWeatherFinished();
            }
        });
    }

    public WeatherCache getWeatherCache() {
        return this.weatherCache;
    }

    // 释放资源 防止内存泄露
    public void onDestroy() {
        this.weatherCache.onDestroy();
        // 释放资源
        this.ctx = null;
        this.weatherView = null;
    }

}
