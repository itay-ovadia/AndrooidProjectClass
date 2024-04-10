package com.example.androoidprojectclass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androoidprojectclass.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText signup_BOX_name, signup_BOX_email, signup_BOX_password, signup_BOX_age, weightEditText, phoneNumberEditText;
    private Button submitButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        findViews();

        submitButton.setOnClickListener(v -> {
            String email = signup_BOX_email.getText().toString();
            String password = signup_BOX_password.getText().toString();

            if (validateUserInput()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign-up successful
                                FirebaseUser user = mAuth.getCurrentUser();
                                saveUserData(user);
                                Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                // Sign-up failed
                                Toast.makeText(SignUpActivity.this, "Sign Up Failed. Enter valid details and try again", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Display a toast message if validation fails
                Toast.makeText(SignUpActivity.this, "Sign Up Failed. Enter valid details and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        signup_BOX_name = findViewById(R.id.signup_BOX_name);
        signup_BOX_email = findViewById(R.id.signup_BOX_email);
        signup_BOX_password = findViewById(R.id.signup_BOX_password);
        signup_BOX_age = findViewById(R.id.signup_BOX_age);
        weightEditText = findViewById(R.id.signup_BOX_weight);
        phoneNumberEditText = findViewById(R.id.signup_BOX_phoneNumber);
        submitButton = findViewById(R.id.signupbuttonSubmit);
    }

    private void saveUserData(FirebaseUser user) {
        String userId = user.getUid();
        String name = signup_BOX_name.getText().toString();
        int age = Integer.parseInt(signup_BOX_age.getText().toString());
        float weight = Float.parseFloat(weightEditText.getText().toString());
        String phoneNumber = phoneNumberEditText.getText().toString();

        // Save the user's basic information to the Realtime Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.child("name").setValue(name);
        userRef.child("age").setValue(age);
        userRef.child("weight").setValue(weight);
        userRef.child("phoneNumber").setValue(phoneNumber);

        // Create a new "workouts" node for the user in the Realtime Database
        DatabaseReference workoutsRef = userRef.child("workouts");

        // Add an example workout
        Map<String, Object> exampleWorkout = new HashMap<>();
        exampleWorkout.put("name", "Push-Ups");
        exampleWorkout.put("duration", 60); // in seconds
        exampleWorkout.put("imageResource", R.drawable.cal1);
        exampleWorkout.put("instructions", "Start in a high plank position...");
        exampleWorkout.put("sets", 3);
        exampleWorkout.put("reps", 10);

        String workoutKey = workoutsRef.push().getKey();
        workoutsRef.child(workoutKey).setValue(exampleWorkout);
    }
    public boolean checkFullName(String fullName) {
        // Regular expression to match one or more occurrences of letters and spaces at the beginning of the string
        String regex = "^(?!.{51})[a-zA-Z-]+(?: [a-zA-Z]+(?: [a-zA-Z-]+)?)?$";

        // Check if the full name matches the regular expression
        if (fullName.matches(regex)) {
            return true; // Full name is entered correctly
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Name, Try Again", Toast.LENGTH_SHORT).show();
            return false; // Full name is not entered correctly
        }
    }


    public boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            return true; // Email is valid
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Email, Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
            return false; // Email is invalid
        }
    }
    public boolean checkPassword(String password) {
        // Regular expression to match a password with specific criteria
        String regex = "^[a-zA-Z0-9]{6,20}$";
        if (password.matches(regex)) {
            return true; // Password is valid
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Password. Password must contain at least 8 characters with one digit, one uppercase letter, one lowercase letter, and one special character.", Toast.LENGTH_SHORT).show();
            return false; // Password is invalid
        }
    }
    public boolean checkAge(int age) {
        if (age >= 18 && age <= 100) {
            return true; // Age is within the valid range
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Age. " +
                    "Please enter an age between 18 and 100.", Toast.LENGTH_SHORT).show();
            return false; // Age is outside the valid range
        }
    }

    public boolean checkWeight(float weight) {
        if (weight >= 10 && weight <= 500) {
            return true; // Weight is within the valid range
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Weight." +
                    " Please enter a weight between 10 and 500 kg.", Toast.LENGTH_SHORT).show();
            return false; // Weight is outside the valid range
        }
    }

    public boolean checkPhone(String phoneNumber) {
        // Regular expression to match a phone number with optional country code and dashes
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        if (phoneNumber.matches(regex)) {
            return true; // Phone number is in the correct format
        } else {
            Toast.makeText(SignUpActivity.this, "Invalid Phone Number. Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return false; // Phone number is in an incorrect format
        }
    }

    private boolean validateUserInput() {
        String name = signup_BOX_name.getText().toString();
        String email = signup_BOX_email.getText().toString();
        String password = signup_BOX_password.getText().toString();
        int age = Integer.parseInt(signup_BOX_age.getText().toString());
        float weight = Float.parseFloat(weightEditText.getText().toString());
        String phoneNumber = phoneNumberEditText.getText().toString();

        if (checkFullName(name) && checkEmail(email) && checkPassword(password) && checkAge(age) && checkWeight(weight) && checkPhone(phoneNumber)) {
            return true; // All details are valid
        } else {
            Toast.makeText(SignUpActivity.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
            return false; // Invalid input detected
        }
    }

}