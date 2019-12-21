package com.panrongda.qingweather;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.panrongda.qingweather.adapter.ForecastAdapter;
import com.panrongda.qingweather.cache.WeatherCache;
import com.panrongda.qingweather.mvp.presenter.WeatherPresenter;
import com.panrongda.qingweather.mvp.view.WeatherView;
import com.panrongda.qingweather.util.Constant;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;

// 天气详情界面
public class WeatherActivity extends AppCompatActivity implements WeatherView {

    private static final String TAG = "WeatherActivity";        /* Log Tag */

    TextView           locationTv;        // 城市
    TextView           updateTimeTv;      // 更新时间
    TextView           tmpTv;             // 温度
    TextView           condTv;            // 天气状况
    RecyclerView       forecastList;      // 天气预报
    TextView           fengXiangTv;       // 风向
    TextView           tiGanWenDuTv;      // 体感温度
    TextView           shiDuTv;           // 湿度
    TextView           nengJianDuTv;      // 能见度
    TextView           qiYaTv;            // 气压
    TextView           yunLiangTv;        // 云量
    TextView           lifeStyleTv;       // 生活指数
    SwipeRefreshLayout refreshLayout;

    private WeatherCache     weatherCache;
    private WeatherPresenter weatherPresenter;

    private ForecastAdapter adapter;
    private String          location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        location = intent.getStringExtra(Constant.LOCATION);

        initViews();
        initEvent();
        initPresenter();
        initRecycleView();
        initData();
    }

    private void initViews() {
        locationTv = findViewById(R.id.location);
        updateTimeTv = findViewById(R.id.update_time);
        tmpTv = findViewById(R.id.tmp);
        condTv = findViewById(R.id.cond_txt);
        forecastList = findViewById(R.id.forecast_list);
        fengXiangTv = findViewById(R.id.fengxiang);
        tiGanWenDuTv = findViewById(R.id.wendu);
        shiDuTv = findViewById(R.id.shidu);
        nengJianDuTv = findViewById(R.id.nengjiandu);
        qiYaTv = findViewById(R.id.qiya);
        yunLiangTv = findViewById(R.id.yunliang);
        lifeStyleTv = findViewById(R.id.lifestyle);
        refreshLayout = findViewById(R.id.refresh_weather);
    }

    private void initEvent() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherPresenter.getWeather(location);
            }
        });
    }

    private void initPresenter() {
        weatherPresenter = new WeatherPresenter(this, this);
    }

    private void initRecycleView() {
        adapter = new ForecastAdapter();
        forecastList.setAdapter(adapter);
        forecastList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        weatherCache = WeatherCache.getInstance();

        if (!TextUtils.isEmpty(location)) {
            Weather weather = weatherCache.get(location);
            if (weather != null) {
                setDataToViews(weather);
            } else {
                weatherPresenter.getWeather(location);
            }
        } else {
            weatherPresenter.getWeather();
        }
    }

    private void setDataToViews(Weather weather) {
        List<LifestyleBase> lifestyle     = weather.getLifestyle();
        LifestyleBase       lifestyleBase = lifestyle.get(0);
        // 城市 更新时间 温度 天气状况 风向 体感温度 湿度 能见度 气压 云量 生活指数
        String location         = weather.getBasic().getLocation();
        String updateTime       = weather.getUpdate().getLoc();
        String tmp              = weather.getNow().getTmp() + "°";
        String cond_txt         = weather.getNow().getCond_txt();
        String wind_dir         = weather.getNow().getWind_dir();
        String fl               = weather.getNow().getFl() + "°";
        String hum              = weather.getNow().getHum() + "%";
        String vis              = weather.getNow().getVis() + "千米";
        String pres             = weather.getNow().getPres() + "百帕";
        String cloud            = weather.getNow().getCloud() + "朵";
        String lifestyleBaseTxt = lifestyleBase.getTxt();

        locationTv.setText(location);
        updateTimeTv.setText(updateTime);
        tmpTv.setText(tmp);
        condTv.setText(cond_txt);
        fengXiangTv.setText(wind_dir);
        tiGanWenDuTv.setText(fl);
        shiDuTv.setText(hum);
        nengJianDuTv.setText(vis);
        qiYaTv.setText(pres);
        yunLiangTv.setText(cloud);
        lifeStyleTv.setText(lifestyleBaseTxt);

        adapter.setData(weather.getDaily_forecast());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.weatherPresenter.onDestroy();
        this.adapter = null;
        this.weatherCache = null;
    }

    @Override
    public void onWeatherRequestStart() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onWeatherFinished() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onWeatherLoad(Weather weather) {
        LogUtils.dTag(TAG, "onWeatherLoad: --- > weather = " + new Gson().toJson(weather));
        setDataToViews(weather);
    }

    @Override
    public void onWeatherFailed(Code code) {
        LogUtils.eTag(TAG, "onWeatherFailed: --- > err code = " + code);
    }

    @Override
    public void onWeatherError(Throwable e) {
        LogUtils.eTag(TAG, "onWeatherError: --- > err = " + e);
    }
}
