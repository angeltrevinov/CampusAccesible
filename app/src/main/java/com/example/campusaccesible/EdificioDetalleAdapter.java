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

public class EdificioDetalleAdapter extends RecyclerView.Adapter<EdificioDetalleAdapter.ViewHolder> {

    private List<Detalles> detalleEdificios;
    public Context context;

    // Constructor
    public EdificioDetalleAdapter(List<Detalles> itemList, Context context) {
        this.context = context;
        this.detalleEdificios = itemList;
    }

    @NonNull
    @Override
    public EdificioDetalleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edificiodetalle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Detalles currentItem = detalleEdificios.get(position);

        holder.textNommbre.setText(currentItem.getStrNombre());
        Glide.with(context).load(currentItem.getImgIconURL()).into(holder.imgIcono);
        Glide.with(context).load(currentItem.getImgCheckURL()).into(holder.imgCheck);
    }

    @Override
    public int getItemCount() {
        return detalleEdificios.size();
    }


    // Funcion para realizar el binding
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Variables de la actividad
        TextView textNommbre;
        ImageView imgIcono;
        ImageView imgCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNommbre = itemView.findViewById(R.id.idTitulo);
            imgIcono  =  itemView.findViewById(R.id.idIcon);
            imgCheck = itemView.findViewById(R.id.idCheck);
        }
    }
}
