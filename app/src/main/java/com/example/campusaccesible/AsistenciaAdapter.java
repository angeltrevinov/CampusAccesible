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

public class AsistenciaAdapter extends  RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>{

    private Context mContext;

    private List<Asistencia> mAsistenciaList;

    public AsistenciaAdapter(Context context, List<Asistencia> AsistenciaList){
        mContext = context;
        mAsistenciaList = AsistenciaList;
    }

    @NonNull
    @Override
    public AsistenciaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.aisistencia_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciaAdapter.ViewHolder holder, int position) {

        Asistencia currentItem = mAsistenciaList.get(position);
        String Titulo = currentItem.getTitulo();
        String Subtitulo = currentItem.getDescripcion();
        String image = currentItem.getImgUrl();

        holder.mTitulo.setText(Titulo);
        holder.mSubtitulo.setText(Subtitulo);
        Glide.with(mContext).load(image).centerCrop().into(holder.mImageView);
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


