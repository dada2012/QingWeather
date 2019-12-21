package com.panrongda.qingweather.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.panrongda.qingweather.base.BaseApplication;
import com.panrongda.qingweather.mvp.view.CityView;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * Create by Dada on 2019/12/17 6:43.
 * 城市数据请求
 */
public class CityPresenter {

    private static final String TAG = "CityPresenter";        /* Log Tag */
    private static final String CN  = "CN";

    private static int    cityNum = 10;
    private static String country = CN;

    private Context  ctx;
    private CityView cityView;

    public CityPresenter(CityView cityView, Context context) {
        this.cityView = cityView;
        this.ctx = context == null ? BaseApplication.getContext() : context;
    }

    /**
     * 根据当前位置查询城市信息
     */
    public void getCity() {
        HeWeather.getSearch(ctx, cityNum, new HeWeather.OnResultSearchBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                cityView.onCityError(throwable);
            }

            @Override
            public void onSuccess(Search search) {
                LogUtils.dTag(TAG, "search = " + new Gson().toJson(search));
                String status = search.getStatus();
                if (Code.OK.getCode().equalsIgnoreCase(status)) {
                    List<Basic> basicList = search.getBasic();
                    cityView.onCityLoad(basicList);
                } else {
                    cityView.onCityFailed(Code.toEnum(status));
                }

            }
        });
    }

    /**
     * 根据城市名查询城市信息 (可模糊查询)
     *
     * @param city 城市名
     */
    public void getCity(String city) {
        HeWeather
                .getSearch(ctx, city, country, cityNum, Lang.CHINESE_SIMPLIFIED, new HeWeather.OnResultSearchBeansListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        cityView.onCityError(throwable);
                    }

                    @Override
                    public void onSuccess(Search search) {
                        String status = search.getStatus();
                        if (Code.OK.getCode().equalsIgnoreCase(status)) {
                            List<Basic> basicList = search.getBasic();
                            cityView.onCityLoad(basicList);
                        } else {
                            Code code = Code.toEnum(status);
                            cityView.onCityFailed(code);
                        }
                    }
                });
    }

    // 释放资源 防止内存泄露
    public void onDestroy() {
        this.ctx = null;
        this.cityView = null;
    }

}
