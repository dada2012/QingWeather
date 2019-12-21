package com.panrongda.qingweather;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.panrongda.qingweather.adapter.CityResultAdapter;
import com.panrongda.qingweather.mvp.presenter.CityPresenter;
import com.panrongda.qingweather.mvp.view.CityView;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;

// 天气搜索界面
public class CitySearchActivity extends AppCompatActivity implements CityView {

    private static final String TAG = "CitySearchActivity";        /* Log Tag */
    // 控件
    EditText     editText;
    ImageButton  backBtn;
    RecyclerView resultListView;
    // 逻辑层
    private CityPresenter     cityPresenter;
    // Adapter
    private CityResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        init();
    }

    private void init() {
        initView();
        initEvent();
        initPresent();
        initRecycleView();
    }

    private void initView() {
        editText = findViewById(R.id.edit_txt);
        backBtn = findViewById(R.id.back_btn);
        resultListView = findViewById(R.id.city_list);
    }

    private void initEvent() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityPresenter.getCity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initPresent() {
        cityPresenter = new CityPresenter(this, this);
    }

    private void initRecycleView() {
        adapter = new CityResultAdapter(this);
        resultListView.setAdapter(adapter);
        resultListView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.onDestroy();
        cityPresenter.onDestroy();
    }

    @Override
    public void onCityLoad(List<Basic> basicList) {
        LogUtils.dTag(TAG, "onCityLoad: --- > basicList = " + new Gson().toJson(basicList));
        adapter.setData(basicList);
    }

    @Override
    public void onCityFailed(Code code) {
        LogUtils.eTag(TAG, "onCityFailed: --- > err code = " + code);
    }

    @Override
    public void onCityError(Throwable e) {
        LogUtils.eTag(TAG, "onCityError: --- > err = " + e);
    }
}
