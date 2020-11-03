package com.example.campusaccesible;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EdificioDetalle extends AppCompatActivity {


    // holder for the recycle view
    private RecyclerView detallesRecyclerView;
    // holder for the building adapter
    private EdificioDetalleAdapter mDetallesAdapter;
    // list that stores the elements we are going to display
    List<Detalles> datos;

    ImageView imgBuilding;

    Intent intent;
    Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edificio_detalle);

        this.imgBuilding = findViewById(R.id.imgBuilding);
        detallesRecyclerView = findViewById(R.id.idRecyDetalles);

        detallesRecyclerView.setHasFixedSize(true);
        detallesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getting pass data
        this.intent = getIntent();
        this.building = (Building) this.intent.getSerializableExtra(BuildingAdapter.CURRRENT_BUILDING);

        // putting name of building in the title
        this.getSupportActionBar().setTitle(this.building.getStrName());

        // putting image of the building
        Glide.with(this).load(this.building.getImgUrl())
                .centerCrop().into(this.imgBuilding);


        generarDatos();

        // creating the adapter
        mDetallesAdapter = new EdificioDetalleAdapter(datos, this);

        // setting the adapter
        detallesRecyclerView.setAdapter(mDetallesAdapter);

    }

    // TODO: delete wen data from building is correct
    public void generarDatos() {
        datos = new ArrayList<>();
        datos.add(new Detalles("Baños", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg"));
        datos.add(new Detalles("Elevador", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg"));
        datos.add(new Detalles("Rampas accesibles", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg", "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg"));
    }

}