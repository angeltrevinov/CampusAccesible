package com.example.campusaccesible;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

// =========================================================
public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    private List<Building> Data;
    public Context context;

    // to store our instance
    public static final String CURRRENT_BUILDING =
            "com.example.twoact.extra.CURRENT_BUILDING";

    // -----------------------------------------------------
    public BuildingAdapter(
            List<Building> itemList,
            Context context
    ) {
        this.context = context;
        this.Data = itemList;
    }

    // -----------------------------------------------------
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

    // -----------------------------------------------------
    /**
     * Connects the holder with the corresponding item to be
     * displayed.
     * @param holder the view
     * @param position the current position in the data
     */
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        final Building currentItem = Data.get(position);

        // set text of the current building
        holder.NamBuildingHolder.setText(currentItem.getStrName());

        // set image of the current building
        Glide.with(context).load(currentItem.getImgUrl())
                .centerCrop().into(holder.img);

        // set event handler for if click
        holder.buildingHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EdificioDetalle.class);
                intent.putExtra(CURRRENT_BUILDING, currentItem);
                context.startActivity(intent);
            }
        });
    }

    // -----------------------------------------------------
    @Override
    public int getItemCount() { return this.Data.size(); }


    // =====================================================
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView NamBuildingHolder;
        View buildingHolder;
        Context context;

        // -------------------------------------------------
        /**
         *  Just gets references of view items from our holder
         * @param itemView
         */
        public  ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageBuilding);
            this.NamBuildingHolder = itemView.findViewById(
                    R.id.buildingNameHolder
            );
            this.buildingHolder = itemView.findViewById(
                    R.id.buildingHolder
            );
        }
    }
}
