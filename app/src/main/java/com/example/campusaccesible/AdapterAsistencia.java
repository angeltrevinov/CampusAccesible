package com.example.campusaccesible;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterAsistencia extends  RecyclerView.Adapter<AdapterAsistencia.ViewHolder>{

    private Context mContext;

    private List<Asistencia> mAsistenciaList;

    public AdapterAsistencia(Context context,List<Asistencia> AsistenciaList){
        mContext = context;
        mAsistenciaList = AsistenciaList;
    }

    @NonNull
    @Override
    public AdapterAsistencia.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.aisistencia_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAsistencia.ViewHolder holder, int position) {

        Asistencia currentItem = mAsistenciaList.get(position);
        String Titulo = currentItem.getTitulo();
        String Subtitulo = currentItem.getDescripcion();
        String image = currentItem.getImgUrl();

        holder.mTitulo.setText(Titulo);
        holder.mSubtitulo.setText(Subtitulo);
        Picasso.with(mContext).load(image).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mAsistenciaList.size();
    }
    //Realiza el binding de los objetos
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitulo;
        public TextView mSubtitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imagen);
            mTitulo  =  itemView.findViewById(R.id.titulo);
            mSubtitulo = itemView.findViewById(R.id.subtitulo);
        }
    }

}


