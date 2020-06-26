package com.bignerdranch.android.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class ShoppingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_activity);

        if (getResources().getConfiguration().orientation == 2) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment uiFragment = manager.findFragmentById(R.id.fragment_container_landscape);
            Fragment listFragment = manager.findFragmentById(R.id.fragment_list_landscape);

            if (uiFragment == null && listFragment == null) {
                uiFragment = new UIFragment();
                listFragment = new ListFragment();
                manager.beginTransaction()
                        .add(R.id.fragment_container_landscape, uiFragment)
                        .add(R.id.fragment_list_landscape, listFragment)
                        .commit();
            }
        } else {
            FragmentManager manager = getSupportFragmentManager();
            Fragment uiFragment = manager.findFragmentById(R.id.fragment_container_portrait);

            if (uiFragment == null) {
                uiFragment = new UIFragment();
                manager.beginTransaction()
                        .add(R.id.fragment_container_portrait, uiFragment)
                        .commit();
            }
        }
    }

}
