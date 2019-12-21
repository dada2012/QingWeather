package com.panrongda.qingweather;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.panrongda.qingweather.adapter.SimpleWeatherAdapter;
import com.panrongda.qingweather.cache.WeatherCache;
import com.panrongda.qingweather.mvp.presenter.WeatherPresenter;
import com.panrongda.qingweather.mvp.view.WeatherView;
import com.panrongda.qingweather.util.Constant;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

// 主界面
public class MainActivity extends AppCompatActivity implements WeatherView {

    private static final String TAG = "MainActivity";        /* Log Tag */
    // 控件
    RecyclerView         cityListView;
    FloatingActionButton addCityBtn;
    SwipeRefreshLayout   refreshLayout;
    // RecycleView Adapter
    private SimpleWeatherAdapter simpleCityAdapter;
    // 逻辑层
    private WeatherPresenter     weatherPresenter;
    // 缓存
    private WeatherCache         weatherCache;

    private final int CITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initView();         // 控件
        initEvent();        // 事件
        initPresenter();    // 逻辑层
        initRecycleView();  // 列表
        initData();         // 数据
    }

    // 控件初始化
    private void initView() {
        cityListView = findViewById(R.id.city_list);
        addCityBtn = findViewById(R.id.add_city_btn);
        refreshLayout = findViewById(R.id.refresh_weather_main);
    }

    // 事件初始化
    private void initEvent() {
        addCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CitySearchActivity.class);
                startActivityForResult(intent, CITY_REQUEST);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showShort("数据已是最新,再拉也是没有用哒~~~");
                refreshLayout.setRefreshing(false);
            }
        });
    }

    // 实例化逻辑层
    private void initPresenter() {
        weatherPresenter = new WeatherPresenter(this, this);
        weatherCache = weatherPresenter.getWeatherCache();
    }

    // 初始化 recycleView
    private void initRecycleView() {
        simpleCityAdapter = new SimpleWeatherAdapter(this);
        cityListView.setAdapter(simpleCityAdapter);
        cityListView
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 设置边距
        cityListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int space = 8;
                outRect.bottom = space;
                outRect.top = space;
            }
        });
    }

    // 数据初始化
    private void initData() {
        // 从缓存中获取
        List<Weather> weatherList = weatherCache.readFromFile();
        if (weatherList != null && weatherList.size() > 0) {
            // 直接设置数据
            simpleCityAdapter.addData(weatherList);
        } else {
            // 从服务器获取
            weatherPresenter.getWeather();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CITY_REQUEST:
                if (resultCode == RESULT_OK) {
                    String location = data.getStringExtra(Constant.LOCATION);
                    if (!TextUtils.isEmpty(location)) {
                        weatherPresenter.getWeather(location);
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleCityAdapter.onDestroy();
        weatherPresenter.onDestroy();
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
        LogUtils.dTag(TAG, "weather = " + new Gson().toJson(weather));
        simpleCityAdapter.addData(weather);
    }

    @Override
    public void onWeatherFailed(Code code) {
        LogUtils.eTag(TAG, "failed code = " + code);
        ToastUtils.showShort("天气数据请求失败");
    }

    @Override
    public void onWeatherError(Throwable e) {
        LogUtils.eTag("onError -- > err = " + e);
        ToastUtils.showShort("天气数据请求失败");
    }
}
