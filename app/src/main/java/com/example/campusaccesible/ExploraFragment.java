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

    List<Building> elements;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploraFragment() {
        // Required empty public constructor
    }

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Set the title of the the activity
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Explora");
        //this.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explora, container, false);
    }

    public void init() {
        this.elements = new ArrayList<>();
        elements.add(new Building("Edificio 1"));
        elements.add(new Building("Edificio 2"));
        elements.add(new Building("Edificio 3"));
        elements.add(new Building("Edificio 4"));
        elements.add(new Building("Edificio 5"));



        BuildingAdapter buildingAdapter = new BuildingAdapter(
                elements,
                (( MainActivity) getActivity())
        );
        RecyclerView recyclerView =
                (( MainActivity) getActivity()).findViewById(
                        R.id.listRecyclerView
                );
        recyclerView.setLayoutManager(new LinearLayoutManager(
                (( MainActivity) getActivity()))
        );
        recyclerView.setAdapter(buildingAdapter);
    }
}