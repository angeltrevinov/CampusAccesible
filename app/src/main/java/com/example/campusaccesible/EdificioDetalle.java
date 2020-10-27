package com.example.campusaccesible;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EdificioDetalle extends AppCompatActivity {


    // holder for the recycle view
    private RecyclerView detallesRecyclerView;
    // holder for the building adapter
    private EdificioDetalleAdapter mDetallesAdapter;
    // list that stores the elements we are going to display
    List<Detalles> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edificio_detalle);

        detallesRecyclerView = findViewById(R.id.idRecyDetalles);

        detallesRecyclerView.setHasFixedSize(true);
        detallesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        generarDatos();

        // creating the adapter
        mDetallesAdapter = new EdificioDetalleAdapter(datos, this);

        // setting the adapter
        detallesRecyclerView.setAdapter(mDetallesAdapter);

    }

    public void generarDatos() {
        datos = new ArrayList<>();
        datos.add(new Detalles("Baños", "", ""));
        datos.add(new Detalles("Elevador", "", ""));
        datos.add(new Detalles("Rampas accesibles", "", ""));
    }

}