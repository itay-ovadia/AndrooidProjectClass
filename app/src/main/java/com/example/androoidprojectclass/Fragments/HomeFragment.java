package com.example.androoidprojectclass.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.androoidprojectclass.Activities.AllCalisthenicsActivity;
import com.example.androoidprojectclass.Activities.AllYogaActivity;
import com.example.androoidprojectclass.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Get references to the Yoga and Calisthenics buttons
        Button yogaButton = view.findViewById(R.id.choose_BTN_yoga);
        Button calisthenicsButton = view.findViewById(R.id.choose_BTN_calisthenics);

        // Set click listeners for the buttons
        yogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AllYogaActivity when the Yoga button is clicked
                Intent intent = new Intent(getActivity(), AllYogaActivity.class);
                startActivity(intent);
            }
        });

        calisthenicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AllCalisthenicsActivity when the Calisthenics button is clicked
                Intent intent = new Intent(getActivity(), AllCalisthenicsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}