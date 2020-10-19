package com.example.campusaccesible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    private List<Building> Data;
    private Context context;

    public BuildingAdapter(List<Building> itemList, Context context) {
        this.context = context;
        this.Data = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.building_element,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        Building currentItem = Data.get(position);
        holder.NamBuildingHolder.setText(currentItem.getStrName());

        Glide.with(context).load(currentItem.getImgUrl()).centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() { return this.Data.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView NamBuildingHolder;

       public  ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageBuilding);
            this.NamBuildingHolder = itemView.findViewById(R.id.buildingNameHolder);
        }
    }
}
