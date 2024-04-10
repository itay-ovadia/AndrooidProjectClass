package com.example.androoidprojectclass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.androoidprojectclass.Fragments.HomeFragment;
import com.example.androoidprojectclass.Fragments.ProfileFragment;
import com.example.androoidprojectclass.Fragments.WorkoutsFragment;
import com.example.androoidprojectclass.Models.UserProfile;
import com.example.androoidprojectclass.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class YogaDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private Button addWorkoutButton;
    private Button goToProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_details);

        // Initialize Firebase Auth and Realtime Database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Retrieve the workout data from the intent
        String workoutName = getIntent().getStringExtra("workoutName");
        int workoutImage = getIntent().getIntExtra("workoutImage", 0);
        String workoutInstructions = getIntent().getStringExtra("workoutInstructions");
        int workoutDuration = getIntent().getIntExtra("workoutDuration", 0);

        // Populate the views with the workout details
        ImageView imageView = findViewById(R.id.workout_image);
        if (imageView != null) {
            imageView.setImageResource(workoutImage);
        }

        TextView nameTextView = findViewById(R.id.workout_name);
        nameTextView.setText(workoutName);

        TextView durationTextView = findViewById(R.id.workout_duration);
        durationTextView.setText("Duration: " + workoutDuration + " minutes");

        TextView instructionsTextView = findViewById(R.id.workout_instructions);
        instructionsTextView.setText(workoutInstructions);

        // Add the "Add to Workouts" button
        addWorkoutButton = findViewById(R.id.add_workout_button);
        addWorkoutButton.setOnClickListener(v -> {
            saveWorkoutToDatabase(workoutName, workoutDuration, workoutImage, workoutInstructions);
        });

        // Add the "Go to My Profile" button
        goToProfileButton = findViewById(R.id.go_to_profile_button);
        goToProfileButton.setOnClickListener(v -> {
            navigateToUserProfile();
        });

        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment fragment;
            if (itemId == R.id.navigation_home) {
                fragment = new HomeFragment();
                navigateToFragment(fragment);
            } else if (itemId == R.id.navigation_workouts) {
                fragment = new WorkoutsFragment();
                navigateToFragment(fragment);
            } else if (itemId == R.id.navigation_profile) {
                fragment = new ProfileFragment();
                navigateToFragment(fragment);
            } else {
                return false;
            }
            return true;
        });

        // Add the initial HomeFragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void saveWorkoutToDatabase(String name, int duration, int imageResource, String instructions) {
        // Get the current user's UID
        String userId = mAuth.getCurrentUser().getUid();

        // Get a reference to the user's "workouts" node
        DatabaseReference workoutsRef = usersRef.child(userId).child("workouts");

        // Create a new workout object
        Map<String, Object> newWorkout = new HashMap<>();
        newWorkout.put("name", name);
        newWorkout.put("duration", duration);
        newWorkout.put("imageResource", imageResource);
        newWorkout.put("instructions", instructions);

        // Get a new child reference for the workout
        DatabaseReference newWorkoutRef = workoutsRef.push();

        // Save the workout data to the new child node
        newWorkoutRef.setValue(newWorkout);
        Toast.makeText(this, "Workout added to your workouts!", Toast.LENGTH_SHORT).show();
    }

    private void navigateToUserProfile() {
        // Implement the logic to navigate to the user's profile screen
        // For example, you can start a new activity:
        Intent intent = new Intent(YogaDetailsActivity.this, UserProfile.class);
        startActivity(intent);
    }
}