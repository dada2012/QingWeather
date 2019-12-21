package com.panrongda.qingweather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panrongda.qingweather.R;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;

/**
 * Create by Dada on 2019/12/21 18:41.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastBase> forecastBaseList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastBase forecastBase = forecastBaseList.get(position);
        String       date         = forecastBase.getDate();
        String       cond         = forecastBase.getCond_txt_d();
        String       wind_dir     = forecastBase.getWind_dir();
        String       hum          = forecastBase.getHum() + "%";
        String       tmp_max      = forecastBase.getTmp_max() + "°";
        String       tmp_min      = forecastBase.getTmp_min() + "°";

        holder.dateTv.setText(date);
        holder.condTv.setText(cond);
        holder.fengXiangTv.setText(wind_dir);
        holder.shiDuTv.setText(hum);
        holder.tmpMaxTv.setText(tmp_max);
        holder.tmpMinTv.setText(tmp_min);
    }

    @Override
    public int getItemCount() {
        return forecastBaseList == null ? 0 : forecastBaseList.size();
    }

    public void setData(List<ForecastBase> forecastBaseList) {
        this.forecastBaseList.clear();
        this.forecastBaseList.addAll(forecastBaseList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        TextView condTv;
        TextView tmpMinTv;
        TextView tmpMaxTv;
        TextView fengXiangTv;
        TextView shiDuTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date);
            condTv = itemView.findViewById(R.id.cond);
            tmpMinTv = itemView.findViewById(R.id.tmp_min);
            tmpMaxTv = itemView.findViewById(R.id.tmp_max);
            fengXiangTv = itemView.findViewById(R.id.wind_dir);
            shiDuTv = itemView.findViewById(R.id.um);
        }
    }
}
