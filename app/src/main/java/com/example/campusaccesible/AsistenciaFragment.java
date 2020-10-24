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
 * Use the {@link AsistenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsistenciaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private AdapterAsistencia mAdapterAsistencia;
    private List<Asistencia> mAsistencia;


    public AsistenciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsistenciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsistenciaFragment newInstance(String param1, String param2) {
        AsistenciaFragment fragment = new AsistenciaFragment();
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

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Asistencia");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_asistencia, container, false);

        mAsistencia = new ArrayList<>();

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));


        //mRequestQueue = Volley.newRequestQueue(getActivity());
        mAsistencia.add(new Asistencia("Cambio de Salon","Sugerir cambio de salon para que el traslado sea mas efectivo y efeciciente",null));
        mAsistencia.add(new Asistencia("Silla de Ruedas","Pedir una silla de ruedas para poder usarla en el campus, esto ayudara a trasladar al discapacitado",null));
        mAsistencia.add(new Asistencia("Asistencia Personal","En dado caso que el discapacitado necesite ayuda de alguna persona, el campus le brindara la ayuda que necesite",null));

        mAdapterAsistencia = new AdapterAsistencia(rootView.getContext(),mAsistencia);

        mRecyclerView.setAdapter(mAdapterAsistencia);
        return rootView;
    }
}