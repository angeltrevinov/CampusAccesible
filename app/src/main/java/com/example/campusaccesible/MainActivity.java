package com.example.campusaccesible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    static public String TAG = "FIREBASE_MSG";

    // holder for the bottom navigation bar
    BottomNavigationView bottomNav;

    // our firestore instance
    FirebaseFirestore firestore;

    // -----------------------------------------------------
    /**
     * The on Create method, initialize methods and variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the navigation bar
        this.bottomNav = findViewById(R.id.bottom_navigation);

        // show the Explora fragment and set it as selected
        // in the navigation view
        loadFragment(new ExploraFragment());
        this.bottomNav.setSelectedItemId(R.id.explora_menu_button);

        // listen to navigation bar events
        this.bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        // Firestore
        this.firestore = FirebaseFirestore.getInstance();
        this.getBuildingsFromFirestore();

    }

    // -----------------------------------------------------
    /**
     * replaces the current home fragment with the one it just selected
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(
                        R.id.fragment,
                        fragment
                ).commit();
    }

    // -----------------------------------------------------
    /**
     * Method that listens for navigation changes and calls
     * the loadFragment to load the corresponding fragment.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.explora_menu_button:
                            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                            getSupportActionBar().setCustomView(R.layout.action_bar_layout);
                            selectedFragment = new ExploraFragment();
                            loadFragment(selectedFragment);
                            return true;
                        case R.id.mapa_menu_button:
                            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                            getSupportActionBar().setCustomView(R.layout.action_bar_mapa);
                            selectedFragment = new MapFragment();
                            loadFragment(selectedFragment);
                            return true;
                        case R.id.asistencia_menu_button:
                            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                            getSupportActionBar().setCustomView(R.layout.action_bar_asistencia);
                            selectedFragment = new AsistenciaFragment();
                            loadFragment(selectedFragment);
                            return true;
                    }
                    return false;
                }
            };

    // -----------------------------------------------------
    public void getBuildingsFromFirestore() {
        this.firestore.collection("location")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + "=>" + documentSnapshot.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}