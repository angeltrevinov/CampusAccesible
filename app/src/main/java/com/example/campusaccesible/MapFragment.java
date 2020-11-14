package com.example.campusaccesible;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    List<Building> buildingList;
    FirebaseFirestore firestore;

    //Ambos Dropdown
    AutoCompleteTextView opcion_destino;
    AutoCompleteTextView opcion_origen;

    GoogleMap map;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Mapa");

        // Firestore
        this.firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        this.buildingList = new ArrayList<>();
        this.getBuildingsFromFirestore();

        //Dropdown de Input destination
        opcion_destino = rootView.findViewById(R.id.destination);
        opcion_destino.setAdapter(new ArrayAdapter<Building>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.buildingList
        ));
        opcion_destino.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // TODO: put marker on the map for the item selected
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Building) {
                    Building selectedBuilding = (Building) item;
                    showToast("" + selectedBuilding.getStrName());
                }
            }
        });

        //Dropdown de Input Origin
        opcion_origen = rootView.findViewById(R.id.origin);
        opcion_origen.setAdapter(new ArrayAdapter<Building>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.buildingList
        ));
        opcion_origen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // TODO: put marker on the map for the item selected
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Building) {
                    Building selectedBuilding = (Building) item;
                    showToast("" + selectedBuilding.getStrName());
                }
            }
        });

        //Encontrar el mapa
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Initialize fused Location
        client = LocationServices.getFusedLocationProviderClient(this.getActivity());

        // Check permission
        if (ActivityCompat.checkSelfPermission(MapFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission granted call method
            getCurrentLocation();
        }
        else{
            // When permission denied request permission
            ActivityCompat.requestPermissions(MapFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }




        return rootView;
    }

    private void getCurrentLocation() {
        // Initialize task location
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                // When success
                if(location != null){
                    // Sync map
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            // Initialize lat lng
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            // Create marker options
                            //CircleOptions options = new CircleOptions().center(latLng).radius(1);

                            // Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

                            // Add marker on map
                            //googleMap.addCircle(options);
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // When permission granted call method
                getCurrentLocation();
            }
        }
    }

    // -----------------------------------------------------
    /**
     * Method that gets the list of buildings from Firestore
     */
    public void getBuildingsFromFirestore() {
        this.firestore.collection(Constants.COLLECTION_LOCATION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Building currentItem = documentSnapshot.toObject(Building.class);
                                currentItem.setStrId(documentSnapshot.getId());
                                buildingList.add(currentItem);
                            }
                        } else {
                            Log.w(Constants.FIREBASE_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // TODO: delete
    void showToast(
            // helps us display a toast message
            String strMessage
    ){
        Context context = ((MainActivity) getActivity()).getApplicationContext();
        Toast toast = Toast.makeText(context, strMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // When permission granted call method
                getCurrentLocation();
            }
        }
    }
}