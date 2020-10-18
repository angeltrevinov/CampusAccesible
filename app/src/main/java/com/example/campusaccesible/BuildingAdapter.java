package com.example.campusaccesible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    private List<Building> Data;
    private LayoutInflater minflatter;
    private Context context;

    public BuildingAdapter(List<Building> itemList, Context context) {
        this.minflatter = LayoutInflater.from(context);
        this.context = context;
        this.Data = itemList;
    }

    @NonNull
    @Override
    public BuildingAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = this.minflatter.inflate(R.layout.building_element, null);
        return new BuildingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingAdapter.ViewHolder holder, int position) {
        holder.bindData(Data.get(position));
    }

    @Override
    public int getItemCount() { return this.Data.size(); }

    public void setItems(List<Building> items) { this.Data = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView img;
        TextView NamBuildingHolder;

        ViewHolder(View itemView) {
            super(itemView);
            //img = itemView.findViewById(R.id.imageBuilding);
            this.NamBuildingHolder = itemView.findViewById(R.id.buildingNameHolder);
        }

        void bindData(final Building item) {
            this.NamBuildingHolder.setText(item.getStrName());
        }
    }
}
