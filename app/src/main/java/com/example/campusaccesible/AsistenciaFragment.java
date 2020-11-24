package com.example.campusaccesible;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AsistenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsistenciaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AsistenciaAdapter mAsistenciaAdapter;
    private List<Asistencia> mAsistencia;

    FirebaseFirestore firestore;


    public AsistenciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AsistenciaFragment.
     */
    public static AsistenciaFragment newInstance() {
        AsistenciaFragment fragment = new AsistenciaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Asistencia");

        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(
                R.layout.fragment_asistencia,
                container,
                false
        );

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        mAsistencia = new ArrayList<>();
        this.getAsistenciaFromFirestore();

        mAsistenciaAdapter = new AsistenciaAdapter(rootView.getContext(),mAsistencia);

        mRecyclerView.setAdapter(mAsistenciaAdapter);
        return rootView;
    }

    // -----------------------------------------------------
    public void getAsistenciaFromFirestore() {
        this.firestore.collection(Constants.COLLECTION_ASISTENCIA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(Constants.FIREBASE_TAG, documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                                Asistencia currentItem = documentSnapshot.toObject(Asistencia.class);
                                currentItem.setStrId(documentSnapshot.getId());
                                mAsistencia.add(currentItem);
                            }
                            mAsistenciaAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(Constants.FIREBASE_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}