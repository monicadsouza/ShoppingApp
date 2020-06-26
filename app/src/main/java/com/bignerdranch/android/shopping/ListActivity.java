package com.bignerdranch.android.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class ListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager manager = getSupportFragmentManager();
        Fragment listFragment = manager.findFragmentById(R.id.fragment_list);

        if (listFragment == null) {
            listFragment = new ListFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_list, listFragment)
                    .commit();
        }

    }
}
