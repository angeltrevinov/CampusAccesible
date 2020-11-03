package com.example.campusaccesible;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
 * Use the {@link ExploraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploraFragment extends Fragment {

    // holder for the recycle view
    private RecyclerView buildingRecyclerView;
    // holder for the building adapter
    private BuildingAdapter mbuildingAdapter;
    // list that stores the buildingList we are going to display
    List<Building> buildingList;
    // our firestore instance
    FirebaseFirestore firestore;

    // -----------------------------------------------------
    // Required empty public constructor
    public ExploraFragment() {}

    // -----------------------------------------------------
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ExploraFragment.
     */
    public static ExploraFragment newInstance() {
        ExploraFragment fragment = new ExploraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // -----------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the title of the the activity
        ((MainActivity) getActivity()).getSupportActionBar().
                setTitle("Explora");

        // Firestore
        this.firestore = FirebaseFirestore.getInstance();
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

        this.buildingList = new ArrayList<>();

        this.getBuildingsFromFirestore();

        // creating the adapter
        this.mbuildingAdapter = new BuildingAdapter(
                this.buildingList, view.getContext()
        );

        // setting the adapter
        this.buildingRecyclerView.setAdapter(
                this.mbuildingAdapter
        );
        return view;
    }

    // -----------------------------------------------------
    // Just a method that creates our buildings
    public void getBuildingsFromFirestore() {
        this.firestore.collection(Constants.COLLECTION_LOCATION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(Constants.FIREBASE_TAG, documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                                Building currentItem = documentSnapshot.toObject(Building.class);
                                currentItem.setStrId(documentSnapshot.getId());
                                buildingList.add(currentItem);
                            }
                            mbuildingAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(Constants.FIREBASE_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}