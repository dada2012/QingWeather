package com.panrongda.qingweather.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panrongda.qingweather.R;
import com.panrongda.qingweather.util.Constant;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;

/**
 * Create by Dada on 2019/12/21 11:22.
 */
public class CityResultAdapter extends RecyclerView.Adapter<CityResultAdapter.ViewHolder> {

    private List<Basic> basicList = new ArrayList<>();
    private Activity    activity;

    public CityResultAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.city_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Basic  basic       = basicList.get(position);
        final String location    = basic.getLocation();
        String       parent_city = basic.getParent_city();
        String       admin_area  = basic.getAdmin_area();
        String       cnty        = basic.getCnty();

        holder.locationTxt.setText(location);
        holder.parentCityTxt.setText(parent_city);
        holder.adminAreaTxt.setText(admin_area);
        holder.cntyTxt.setText(cnty);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                intent.putExtra(Constant.LOCATION, location);
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return basicList == null ? 0 : basicList.size();
    }

    /**
     * 设置数据
     *
     * @param basicList
     */
    public void setData(List<Basic> basicList) {
        this.basicList.clear();
        this.basicList.addAll(basicList);
        notifyDataSetChanged();
    }

    public void onDestroy() {
        this.activity = null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView locationTxt;      // 城市
        TextView parentCityTxt;    // 上级城市
        TextView adminAreaTxt;     // 行政区域
        TextView cntyTxt;          // 国家

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTxt = itemView.findViewById(R.id.location);
            parentCityTxt = itemView.findViewById(R.id.parent_city);
            adminAreaTxt = itemView.findViewById(R.id.admin_area);
            cntyTxt = itemView.findViewById(R.id.cnty);

        }
    }
}
