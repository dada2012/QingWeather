package com.panrongda.qingweather.mvp.view;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;

/**
 * Create by Dada on 2019/12/17 6:49.
 */
public interface CityView {

    void onCityLoad(List<Basic> basicList);

    void onCityFailed(Code code);

    void onCityError(Throwable e);
}
