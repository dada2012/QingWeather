package com.panrongda.qingweather.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panrongda.qingweather.base.BaseApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

/**
 * Create by Dada on 2019/12/21 16:07.
 */
public class WeatherCache {


    public static final String        WEATHER   = "weather";
    public static final String        CITY      = "cities";
    public static final String        CITY_LIST = "cityList";
    private static      WeatherCache  instance;
    private static      Context       context;
    private static      List<Weather> weatherList;

    private WeatherCache() {
        context = BaseApplication.getContext();
        weatherList = new ArrayList<>();
    }

    public static WeatherCache getInstance() {
        if (instance == null) {
            synchronized (WeatherCache.class) {
                if (instance == null) {
                    instance = new WeatherCache();
                }
            }
        }
        return instance;
    }

    private void add(Weather weather) {
        String location1 = weather.getBasic().getLocation();
        String loc1      = weather.getUpdate().getLoc();

        // 地区相同 和 更新时间相同 就不添加
        Iterator<Weather> iterator = weatherList.iterator();
        while (iterator.hasNext()) {
            Weather next      = iterator.next();
            String  location2 = next.getBasic().getLocation();
            String  loc2      = next.getUpdate().getLoc();
            if (location1.equals(location2)) {
                if (loc1.equals(loc2)) {
                    return;
                } else {
                    iterator.remove();
                }
            }
        }
        weatherList.add(weather);
    }

    private void remove(Weather weather) {
        if (weather == null) return;
        weatherList.remove(weather);
    }

    private void clear() {
        weatherList.clear();
    }

    /**
     * 根据城市名获取天气
     *
     * @param location
     * @return 城市名为空和空字符或未找到返回 null
     */
    public Weather get(String location) {
        if (TextUtils.isEmpty(location)) return null;
        for (Weather w : weatherList) {
            if (location.equals(w.getBasic().getLocation())) return w;
        }
        return null;
    }

    /**
     * 将缓存写入文件
     */
    public void writeToFile() {
        if (weatherList == null) return;
        if (weatherList.size() <= 0) return;

        List<String> locationList = new ArrayList<>();

        // 天气
        for (Weather weather : weatherList) {
            String location = weather.getBasic().getLocation();
            locationList.add(location);
            SharedPreferences sp = context.getSharedPreferences(location, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(WEATHER, new Gson().toJson(weather));
            edit.apply();
        }

        // 城市列表
        SharedPreferences        sp   = context.getSharedPreferences(CITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(CITY_LIST, new Gson().toJson(locationList));
        edit.apply();
    }

    /**
     * 读取数据从文件
     *
     * @return
     */
    public List<Weather> readFromFile() {
        SharedPreferences sp1         = context.getSharedPreferences(CITY, Context.MODE_PRIVATE);
        String            cityListStr = sp1.getString(CITY_LIST, null);
        if (TextUtils.isEmpty(cityListStr)) {
            return null;
        }
        Gson gosn = new Gson();
        List<String> locationList = gosn.fromJson(cityListStr, new TypeToken<List<String>>() {
        }.getType());

        for (String location : locationList) {
            SharedPreferences sp2 = context.getSharedPreferences(location, Context.MODE_PRIVATE);
            String weatherStr = sp2.getString(WEATHER, null);
            if (TextUtils.isEmpty(weatherStr)) {
                return null;
            }
            Weather weather = gosn.fromJson(weatherStr, Weather.class);
            add(weather);
        }
        return weatherList;
    }

    /**
     * 获取缓存的城市
     *
     * @return
     */
    public List<String> readLocations() {
        SharedPreferences sp        = context.getSharedPreferences(CITY_LIST, Context.MODE_PRIVATE);
        String            citiesStr = sp.getString(CITY, null);
        if (!TextUtils.isEmpty(citiesStr)) {
            List<String> locationList = new Gson()
                    .fromJson(citiesStr, new TypeToken<List<String>>() {
                    }.getType());
            return locationList;
        }
        return null;
    }

    /**
     * 刷新
     *
     * @param weather
     */
    public void refresh(Weather weather) {
        remove(weather);
        add(weather);
    }

    public void refresh(List<Weather> weatherList) {
        clear();
        weatherList.addAll(weatherList);
    }

    public void onDestroy() {
        writeToFile();
    }
}
