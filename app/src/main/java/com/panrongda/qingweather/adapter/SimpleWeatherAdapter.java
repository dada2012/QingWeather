package com.panrongda.qingweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panrongda.qingweather.R;
import com.panrongda.qingweather.WeatherActivity;
import com.panrongda.qingweather.base.BaseApplication;
import com.panrongda.qingweather.util.Constant;
import com.panrongda.qingweather.util.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;

/**
 * Create by Dada on 2019/12/17 16:27.
 */
public class SimpleWeatherAdapter extends RecyclerView.Adapter<SimpleWeatherAdapter.ViewHolder> {

    private List<Weather> weatherList = new ArrayList<>();
    private Context       context;

    public SimpleWeatherAdapter(Context context) {
        this.context = context == null ? BaseApplication.getContext() : context;
    }

    /**
     * 添加数据
     */
    public void addData(Weather weather) {
        String location = weather.getBasic().getLocation();
        for (Weather w : this.weatherList) {
            if (w.getBasic().getLocation().equals(location)) {
                return;
            }
        }
        this.weatherList.add(weather);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(List<Weather> weatherList) {
        this.weatherList.clear();
        this.weatherList.addAll(weatherList);
        notifyDataSetChanged();
    }

    public List<Weather> getWeatherList() {
        return this.weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.smple_weather_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.cityName = view.findViewById(R.id.cityName);
        holder.temperature = view.findViewById(R.id.temperature);
        holder.cond = view.findViewById(R.id.condImg);
        holder.condTxt = view.findViewById(R.id.cond_txt);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取数据
        final Weather weather   = weatherList.get(position);
        Basic         basic     = weather.getBasic();
        NowBase       now       = weather.getNow();
        String        cond_code = now.getCond_code();
        String        cond_txt  = now.getCond_txt();
        int           condRes   = WeatherUtil.getCondRes(cond_code);

        // 填充视图
        holder.cityName.setText(basic.getLocation());
        holder.temperature.setText(now.getTmp());
        // holder.cond.setImageResource(condRes);
        holder.condTxt.setText(cond_txt);

        holder.itemView.setBackground(context.getDrawable(WeatherUtil.getBgRes(cond_code)));
        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WeatherActivity.class);
                intent.putExtra(Constant.LOCATION, weather.getBasic().getLocation());
                context.startActivity(intent);
            }
        });
    }

    public void onDestroy() {
        this.context = null;
    }

    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView  cityName;      // 城市名
        TextView  temperature;   // 温度
        TextView  condTxt;      // 天气(文字) 如 晴
        ImageView cond;         // 天气

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
