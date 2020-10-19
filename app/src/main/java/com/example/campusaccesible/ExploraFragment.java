package com.example.campusaccesible;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploraFragment extends Fragment {

    // holder for the recycle view
    private RecyclerView buildingRecyclerView;
    // holder for the building adapter
    private BuildingAdapter mbuildingAdapter;
    // list that stores the elements we are going to display
    List<Building> elements;

/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */

    // -----------------------------------------------------
    // Required empty public constructor
    public ExploraFragment() {}

    // -----------------------------------------------------
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploraFragment newInstance(String param1, String param2) {
        ExploraFragment fragment = new ExploraFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // -----------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Set the title of the the activity
        ((MainActivity) getActivity()).getSupportActionBar().
                setTitle("Explora");
    }

    // -----------------------------------------------------
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.fragment_explora,
                container,
                false
        );

        this.buildingRecyclerView = view.findViewById(
                R.id.listRecyclerView
        );

        this.buildingRecyclerView.setHasFixedSize(true);
        this.buildingRecyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext())
        );

        this.generateBuildingInfo();

        // creating the adapter
        this.mbuildingAdapter = new BuildingAdapter(
                this.elements, view.getContext()
        );

        // setting the adapter
        this.buildingRecyclerView.setAdapter(
                this.mbuildingAdapter
        );
        return view;
    }

    // -----------------------------------------------------
    // Just a method that creates our buildings
    // TODO: This should be an http method and name more
    //  precise
    public void generateBuildingInfo() {
        this.elements = new ArrayList<>();
        elements.add(new Building(
                "",
                "Edificio 1",
                "",
                "",
                "",
                "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg",
                false,
                false,
                false));
        elements.add(new Building(
                "",
                "Edificio 2",
                "",
                "",
                "",
                "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg",
                false,
                false,
                false));
        elements.add(new Building(
                "",
                "Edificio 3",
                "",
                "",
                "",
                "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg",
                false,
                false,
                false));
        elements.add(new Building(
                "",
                "Edificio 4",
                "",
                "",
                "",
                "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg",
                false,
                false,
                false));
        elements.add(new Building(
                "",
                "Edificio 5",
                "",
                "",
                "",
                "https://tec.mx/sites/default/files/2018-12/mural-1920x1080_0.jpg",
                false,
                false,
                false));

    }
}