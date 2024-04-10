package com.example.androoidprojectclass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androoidprojectclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    TextView main_LBL_signin, main_LBL_signup;
    EditText main_BOX_Email, main_BOX_password;
    MaterialButton main_BTN_SignIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        findViews();
        // Initialize Firebase Auth and Realtime Database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        SpannableString spannableString = new SpannableString("New user? Sign Up here.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Start SignUpActivity when "here" is clicked
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };

        // Set the ClickableSpan to the specific text "here"
        spannableString.setSpan(clickableSpan, spannableString.length() - 5, spannableString.length() - 1, 0);

        // Set the SpannableString to the TextView if it's not null
        if (main_LBL_signup != null) {
            main_LBL_signup.setText(spannableString);
            main_LBL_signup.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Load the user's existing workouts data
            loadUserWorkouts(currentUser);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserData(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed. User Not Found\n Try Signing Up",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else {
            // User is signed out
        }
    }

    private void reload() {
        // Reload user information
    }

    private void findViews() {
        main_LBL_signin = findViewById(R.id.main_LBL_signin);
        main_LBL_signup = findViewById(R.id.main_LBL_signup);
        main_BOX_Email = findViewById(R.id.main_BOX_Email);
        main_BOX_password = findViewById(R.id.main_BOX_password);
        main_BTN_SignIn = findViewById(R.id.main_BTN_SignIn);

        main_BTN_SignIn.setOnClickListener(v -> {
            String email = main_BOX_Email.getText().toString();
            String password = main_BOX_password.getText().toString();
            signIn(email, password);
        });
    }

    private void saveUserData(FirebaseUser user) {
        String userId = user.getUid();
        String name = user.getDisplayName();
        String email = user.getEmail();

        // Save the user's basic information to the Realtime Database
        DatabaseReference userRef = usersRef.child(userId);
        userRef.child("name").setValue(name);
        userRef.child("email").setValue(email);
    }

    private void loadUserWorkouts(FirebaseUser user) {
        String userId = user.getUid();
        DatabaseReference workoutsRef = usersRef.child(userId).child("workouts");

        workoutsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Load the user's existing workouts data
                    for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                        Map<String, Object> workout = (Map<String, Object>) workoutSnapshot.getValue();
                        String name = (String) workout.get("name");
                   //     int duration = ((Long) workout.get("duration")).intValue();
                        int imageResource = ((Long) workout.get("imageResource")).intValue();
                  //      String instructions = (String) workout.get("instructions");
                  //      int sets = ((Long) workout.get("sets")).intValue();
                  //      int reps = ((Long) workout.get("reps")).intValue();


                    }
                } else {
                    // Create a new "workouts" node for the user
                    DatabaseReference newWorkoutsRef = usersRef.child(userId).child("workouts");
                    newWorkoutsRef.setValue(new HashMap<>());
                    Log.d(TAG, "Created new workouts node for user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                Log.e(TAG, "Error loading user's workouts data: " + error.getMessage());
            }
        });
    }
}