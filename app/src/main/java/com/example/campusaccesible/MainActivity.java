package com.example.campusaccesible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

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

        // show the home fragment
        loadFragment(new ExploraFragment());
        this.bottomNav.setSelectedItemId(R.id.explora_menu_button);

         // listen to navigation bar events
        this.bottomNav.setOnNavigationItemSelectedListener(navListener);
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.explora_menu_button:
                            selectedFragment = new ExploraFragment();
                            loadFragment(selectedFragment);
                            return true;
                        case R.id.mapa_menu_button:
                            selectedFragment = new MapFragment();
                            loadFragment(selectedFragment);
                            return true;
                        case R.id.asistencia_menu_button:
                            selectedFragment = new AsistenciaFragment();
                            loadFragment(selectedFragment);
                            return true;
                    }
                    return false;
                }
            };
}