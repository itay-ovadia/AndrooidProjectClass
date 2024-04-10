package com.example.androoidprojectclass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androoidprojectclass.R;
import com.example.androoidprojectclass.Models.Yoga;
import com.example.androoidprojectclass.Adapters.YogaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllYogaActivity extends AppCompatActivity {

    private YogaAdapter adapter;
    private List<Yoga> allYogas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_yoga_workouts);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_all_yoga);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter
        allYogas = new ArrayList<>();
        adapter = new YogaAdapter(allYogas);
        recyclerView.setAdapter(adapter);


       // addHardCodedYogaToDatabase();

        // Fetch the yoga workouts from the database
        fetchYogaFromDatabase();
    }

    private void fetchYogaFromDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ifit-mobile-app-default-rtdb.firebaseio.com/");
        DatabaseReference yogaRef = database.getReference("yoga");

        yogaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allYogas.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Yoga yoga = childSnapshot.getValue(Yoga.class);
                    allYogas.add(yoga);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Log.e("AllYogaActivity", "Error fetching yoga workouts: " + error.getMessage());
            }
        });
    }

    private void addHardCodedYogaToDatabase() {
        // Get a reference to the "yoga" node
        DatabaseReference yogaRef = FirebaseDatabase.getInstance("https://ifit-mobile-app-default-rtdb.firebaseio.com/").getReference("yoga");

        // Add the hard-coded yoga workouts to the database
        yogaRef.child("Front Leg Facing dog Workout").setValue(new Yoga(
                "Front Leg Facing Dog Workout",
                8,
                R.drawable.yoga2,
                "Stand with your feet about 3-4 feet apart. Turn your right foot out 90 degrees and your left foot in slightly. Bend your right knee and slide your hips back, keeping your left leg straight. Reach your arms up overhead and look up."
        ));

        yogaRef.child("Downward Facing Dog").setValue(new Yoga(
                "Downward Facing Dog",
                3,
                R.drawable.yoga1,
                "Start on your hands and knees. Tuck your toes under and lift your hips up and back, straightening your legs and forming an inverted V shape with your body. Relax your head between your arms."
        ));

        yogaRef.child("Mega Lunge").setValue(new Yoga(
                "Mega Lunge",
                6,
                R.drawable.yoga3,
                "Step your right foot forward, keeping your left leg straight. Bend your right knee, lowering your hips toward the floor. Reach your arms up overhead, keeping your core engaged."
        ));

        yogaRef.child("Child's Pose").setValue(new Yoga(
                "Child's Pose",
                6,
                R.drawable.yoga4,
                "Start on your hands and knees. Sit your hips back toward your heels, extending your arms forward. Rest your forehead on the mat and breathe deeply."
        ));
        yogaRef.child("Back Leg Reach").setValue(new Yoga(
                "Back Leg Reach",
                1,
                R.drawable.yoga5,
                "Lie on your back on the floor, legs extended and arms at your sides.\n" +
                        "Bend your knees and place your feet flat on the floor, hip-width apart.\n" +
                        "Interlace your fingers behind your back, straightening your arms.\n" +
                        "Inhale and lift your chest, opening your shoulders.\n" +
                        "Exhale and roll your knees to the right, keeping your feet on the floor.\n" +
                        "As you twist, reach your right foot toward your left hand."
        ));
        yogaRef.child("The Astavakrasana Pose").setValue(new Yoga(
                "The Astavakrasana Pose ",
                2,
                R.drawable.yoga6,
                "Sit on the floor, legs extended.\n" +
                        "Cross your right leg over your right shoulder.\n" +
                        "Extend your left leg in front of your right hand.\n" +
                        "Hook your ankles and lean forward to lift your hips.\n" +
                        "Squeeze your inner thighs together.\n" +
                        "Repeat on the other side."
        ));

    }

    private void openYogaDetailsActivity(Yoga yoga) {
        Intent intent = new Intent(AllYogaActivity.this, YogaDetailsActivity.class);
        intent.putExtra("workoutName", yoga.getName());
        intent.putExtra("workoutImage", yoga.getImageResource());
        intent.putExtra("workoutInstructions", yoga.getInstructions());
        intent.putExtra("workoutDuration", yoga.getDuration() + " minutes");
        startActivity(intent);
    }
}