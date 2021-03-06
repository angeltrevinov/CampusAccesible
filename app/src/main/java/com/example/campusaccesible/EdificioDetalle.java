package com.example.campusaccesible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EdificioDetalle extends AppCompatActivity {


    // holder for the recycle view
    private RecyclerView detallesRecyclerView;
    // holder for the building adapter
    private EdificioDetalleAdapter mDetallesAdapter;
    // list that stores the elements we are going to display
    List<Detalles> datos;
    String currentBuilding;
    Building building;

    ImageView imgBuilding;

    Intent intent;

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
        this.currentBuilding = this.intent.getStringExtra(BuildingAdapter.CURRRENT_BUILDING);

        datos = new ArrayList<>();
        this.getBuildingInfo();

        // creating the adapter
        mDetallesAdapter = new EdificioDetalleAdapter(datos, this);

        // setting the adapter
        detallesRecyclerView.setAdapter(mDetallesAdapter);

    }

    public void generarDatos() {
        datos.add(new Detalles(
                "Baños",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Fbathroom.png?alt=media&token=5f273a16-539a-4e8a-8b90-c276242b04ed",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Fcheck.png?alt=media&token=0f3913b0-60ae-454b-bc36-fa6a6dc71796"
        ));
        datos.add(new Detalles(
                "Elevador",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Felevator.png?alt=media&token=3bbbcb12-2623-4d36-a9b4-b25f2716e371",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Fcheck.png?alt=media&token=0f3913b0-60ae-454b-bc36-fa6a6dc71796"
        ));
        datos.add(new Detalles(
                "Rampas accesibles",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Fwheelchair.png?alt=media&token=7f13cb12-6d81-45c9-af8c-7c837c8962fa",
                "https://firebasestorage.googleapis.com/v0/b/campusaccesible-753b5.appspot.com/o/icons%2Fcheck.png?alt=media&token=0f3913b0-60ae-454b-bc36-fa6a6dc71796"
        ));
    }

    // -----------------------------------------------------
    public void getBuildingInfo() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Constants.COLLECTION_LOCATION)
                .document(this.currentBuilding)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.w(Constants.FIREBASE_TAG, "Error getting documents.", task.getException());
                            building = task.getResult().toObject(Building.class);
                            building.setStrId(currentBuilding);
                            displayData();
                        } else {
                            Log.w(Constants.FIREBASE_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // -----------------------------------------------------
    public void displayData() {
        // putting name of building in the title
        this.getSupportActionBar().setTitle(this.building.getStrName());

        // putting image of the building
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child(building.getImgUrl().getPath());
        Glide.with(this).load(storageReference)
                .centerCrop().into(this.imgBuilding);

        generarDatos();
        this.mDetallesAdapter.notifyDataSetChanged();
    }

}