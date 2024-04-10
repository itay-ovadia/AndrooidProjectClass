    package com.example.androoidprojectclass.Models;

    import static android.content.ContentValues.TAG;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.androoidprojectclass.Activities.SignInActivity;
    import com.example.androoidprojectclass.R;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.HashMap;
    import java.util.Map;

    public class UserProfile extends AppCompatActivity {

        private TextView userEmailTextView,userAgeTextView,userWeightTextView,userPhoneTextView;
        private Button signOutButton,addWorkoutButton;
        private LinearLayout workoutContainer;
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference usersRef,workoutsRef;
        private String currentUserId;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_profile);

            findViews();

            // Initialize Firebase Realtime Database
            firebaseDatabase = FirebaseDatabase.getInstance("https://ifit-mobile-app-default-rtdb.firebaseio.com/");
            usersRef = firebaseDatabase.getReference("users");
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            workoutsRef = usersRef.child(currentUserId).child("workouts");

            // Set the user information
            setUserInformation();

            // Set the click listener for the sign-out button
            signOutButton.setOnClickListener(v -> signOut());

            // Set the click listener for the "Add Workout" button, if it's not null
            if (addWorkoutButton != null) {
                addWorkoutButton.setOnClickListener(v -> addWorkoutToDatabase());
            } else {
                Log.e(TAG, "addWorkoutButton is null");
            }
        }

        private void findViews() {
            userEmailTextView = findViewById(R.id.user_email);
            userAgeTextView = findViewById(R.id.user_age);
            userWeightTextView = findViewById(R.id.user_weight);
            userPhoneTextView = findViewById(R.id.user_phone);
            signOutButton = findViewById(R.id.sign_out_button);


        }

        private void setUserInformation() {
            usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userEmail = snapshot.child("email").getValue(String.class);
                        Long userAgeValue = snapshot.child("age").getValue(Long.class);
                        Long userWeightValue = snapshot.child("weight").getValue(Long.class);
                        String userPhone = snapshot.child("phoneNumber").getValue(String.class);

                        String userAge = String.valueOf(userAgeValue);
                        String userWeight = String.valueOf(userWeightValue) + " kg";

                        userEmailTextView.setText(userEmail);
                        userAgeTextView.setText(userAge);
                        userWeightTextView.setText(userWeight);
                        userPhoneTextView.setText(userPhone);

                        // Fetch and display the user's workouts
                       // fetchAndDisplayWorkouts();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors
                    Log.w(TAG, "Failed to read user data.", error.toException());
                }
            });
        }

        private void fetchAndDisplayWorkouts() {
            workoutsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "Workout data snapshot: " + snapshot.getValue());

                    for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                        String workoutName = workoutSnapshot.child("name").getValue(String.class);
                        int workoutDuration = workoutSnapshot.child("duration").getValue(Integer.class);
                        int workoutImageResource = workoutSnapshot.child("imageResource").getValue(Integer.class);
                        String workoutInstructions = workoutSnapshot.child("instructions").getValue(String.class);
                        int workoutSets = workoutSnapshot.child("sets").getValue(Integer.class);
                        int workoutReps = workoutSnapshot.child("reps").getValue(Integer.class);

                        // Create new TextViews to display the workout information
                        TextView workoutNameView = new TextView(UserProfile.this);
                        TextView workoutDurationView = new TextView(UserProfile.this);
                        TextView workoutInstructionsView = new TextView(UserProfile.this);
                        TextView workoutSetsView = new TextView(UserProfile.this);
                        TextView workoutRepsView = new TextView(UserProfile.this);

                        workoutNameView.setText("Workout Name: " + workoutName);
                        workoutDurationView.setText("Duration: " + workoutDuration + " seconds");
                        workoutInstructionsView.setText("Instructions: " + workoutInstructions);
                        workoutSetsView.setText("Sets: " + workoutSets);
                        workoutRepsView.setText("Reps: " + workoutReps);


                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors
                    Log.w(TAG, "Failed to read workouts.", error.toException());
                }
            });
        }

        private void signOut() {
            // Implement the sign-out logic here
            // This could involve clearing the user's session, logging them out, and navigating to the login screen
            Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT).show();

            // Sign out the user
            FirebaseAuth.getInstance().signOut();

            // Navigate to the SignInActivity and finish the current activity
            Intent intent = new Intent(UserProfile.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        private void addWorkoutToDatabase() {
            // Assuming you have the workout data available
            String workoutName = "Push-Ups";
            int workoutDuration = 60; // in seconds
            int workoutImageResource = getWorkoutImageResource(workoutName);
            String workoutInstructions = "Start in a high plank position...";
            int workoutSets = 3;
            int workoutReps = 10;

            // Get a reference to the current user's workouts
            DatabaseReference currentUserWorkoutsRef = usersRef.child(currentUserId).child("workouts");

            // Check if the workout already exists in the user's workouts
            currentUserWorkoutsRef.orderByChild("name").equalTo(workoutName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // The workout already exists, display a toast message
                        Toast.makeText(UserProfile.this, "Workout already exists in your profile.", Toast.LENGTH_SHORT).show();
                    } else {
                        // The workout doesn't exist, add it to the database
                        // Create a new workout object
                        Map<String, Object> newWorkout = new HashMap<>();
                        newWorkout.put("name", workoutName);
                        newWorkout.put("duration", workoutDuration);
                        newWorkout.put("imageResource", workoutImageResource);
                        newWorkout.put("instructions", workoutInstructions);
                        newWorkout.put("sets", workoutSets);
                        newWorkout.put("reps", workoutReps);

                        // Get a new child reference for the workout
                        DatabaseReference newWorkoutRef = currentUserWorkoutsRef.push();

                        // Save the workout data to the new child node
                        newWorkoutRef.setValue(newWorkout);
                        Toast.makeText(UserProfile.this, "Workout added to your workouts!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any errors
                    Log.e(TAG, "Error checking for existing workout: " + error.getMessage());
                }
            });
        }
        private int getWorkoutImageResource(String workoutName) {
            switch (workoutName) {
                case "Dead Hang":
                    return R.drawable.cal2;
                case "Pull-Ups":
                    return R.drawable.cal1;
                case "Push-Ups":
                    return R.drawable.cal4;
                case "Side Plank Hang":
                    return R.drawable.cal3;
                case "Walking Lunge":
                    return R.drawable.cal5;
                case "Hanging L Seats":
                    return R.drawable.cal6;
                case "Upper Back Stretching":
                    return R.drawable.cal7;
                default:
                    return R.drawable.cal1;
            }
        }
    }