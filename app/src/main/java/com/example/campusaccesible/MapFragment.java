package com.example.campusaccesible;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    List<Building> buildingList;
    FirebaseFirestore firestore;

    //Ambos Dropdown
    AutoCompleteTextView opcion_destino;
    AutoCompleteTextView opcion_origen;

    GoogleMap map;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    Marker Origin;
    Marker Destination;
    Polyline polyline = null;
    List<LatLng> LatLnglist = new ArrayList<>();



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

        //Encontrar el mapa
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //Dropdown de Input destination
        opcion_destino = rootView.findViewById(R.id.destination);
        opcion_destino.setAdapter(new ArrayAdapter<Building>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.buildingList
        ));

        //Dropdown de Input Origin
        opcion_origen = rootView.findViewById(R.id.origin);
        opcion_origen.setAdapter(new ArrayAdapter<Building>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.buildingList
        ));

        // Initialize fused Location
        client = LocationServices.getFusedLocationProviderClient(this.getActivity());


        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        googleMap.setMyLocationEnabled(true);
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(MapFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission granted call method

            googleMap.setMyLocationEnabled(true);
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    // When success
                    if (location != null) {
                        // Initialize lat lng
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Zoom map
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    }
                }
            });

            // listener for text view destino
            opcion_destino.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    Object item = parent.getItemAtPosition(position);
                    if (Destination != null) {
                        Destination.remove();
                    }

                    if (polyline != null) {
                        polyline.remove();
                    }

                    if (item instanceof Building) {
                        Building selectedBuilding = (Building) item;
                        Double dLat = Double.parseDouble(selectedBuilding.getStrLatitute());
                        Double dLng = Double.parseDouble(selectedBuilding.getStrLongtitude());

                        LatLng destination = new LatLng(dLat, dLng);
                        Destination = map.addMarker(new MarkerOptions().position(destination).title(selectedBuilding.getStrName()));
                        getRoute();
                    }
                }
            });

            // listener for textview origin
            opcion_origen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    if (Origin != null) {
                        Origin.remove();
                    }

                    if (polyline != null) {
                        polyline.remove();
                    }

                    Object item = parent.getItemAtPosition(position);
                    if (item instanceof Building) {
                        Building selectedBuilding = (Building) item;
                        Double dLat = Double.parseDouble(selectedBuilding.getStrLatitute());
                        Double dLng = Double.parseDouble(selectedBuilding.getStrLongtitude());

                        LatLng origin = new LatLng(dLat, dLng);
                        Origin = map.addMarker(new MarkerOptions().position(origin).title(selectedBuilding.getStrName()));
                        getRoute();
                    }
                }
            });
        } else {
            // When permission denied request permission
            ActivityCompat.requestPermissions(MapFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

            @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    // When success
                    Log.w("getting location", String.valueOf(location));
                    if (location != null) {
                        // Initialize lat lng
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Zoom map
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                        map.setMyLocationEnabled(true);
                    }
                }
            });
        }
    }

    // -----------------------------------------------------
    public void getRoute() {
        if (this.Origin != null && this.Destination != null) {
            LatLng origin = this.Origin.getPosition();
            LatLng dest = this.Destination.getPosition();
            String url = getUrl(origin, dest,"walking");
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // When permission granted call method
            }
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Alternativos caminos
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String ... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();
                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
                // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                //Log.d("onPostExecute","onPostExecute lineoptions decoded");
                polyline = map.addPolyline(lineOptions);
            }
            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                //map.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    class DataParser {
        List<List<HashMap<String,String>>> parse(JSONObject jObject){
            List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
            JSONArray jRoutes;
            JSONArray jLegs;
            JSONArray jSteps;
            try {
                jRoutes = jObject.getJSONArray("routes");
                    /** Traversing all routes */
                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<>();
                        /** Traversing all legs */
                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
                    /** Traversing all steps */
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);
                        /** Traversing all points */
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude) );
                                hm.put("lng", Double.toString((list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            }
            return routes;
        }
        /**
         * Method to decode polyline points
         * */
        private List<LatLng> decodePoly(String encoded) {
            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;
            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;
                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;
                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
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
}
