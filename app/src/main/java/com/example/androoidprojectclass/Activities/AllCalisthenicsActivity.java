package com.example.androoidprojectclass.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androoidprojectclass.Models.Calisthenics;
import com.example.androoidprojectclass.Adapters.CalisthenicsAdapter;
import com.example.androoidprojectclass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCalisthenicsActivity extends AppCompatActivity {

    private CalisthenicsAdapter adapter;
    private List<Calisthenics> allCalisthenics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_calisthenics_workouts);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_all_calisthenics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter
        allCalisthenics = new ArrayList<>();
        adapter = new CalisthenicsAdapter(allCalisthenics);
        recyclerView.setAdapter(adapter);

        // Add the hard-coded calisthenics workouts to the database
       // addHardCodedCalisthenicsToDatabase();

        // Fetch the calisthenics workouts from the database
        fetchCalisthenicsFromDatabase();
    }


    private void fetchCalisthenicsFromDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ifit-mobile-app-default-rtdb.firebaseio.com/");
        DatabaseReference calisthenicsRef = database.getReference("calisthenics");

        calisthenicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCalisthenics.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Calisthenics calisthenics = childSnapshot.getValue(Calisthenics.class);
                    allCalisthenics.add(calisthenics);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Log.e("CalisthenicsActivity", "Error fetching calisthenics workouts: " + error.getMessage());
            }
        });
    }


    private void addHardCodedCalisthenicsToDatabase() {
        // Get a reference to the "calisthenics" node
        DatabaseReference calisthenicsRef = FirebaseDatabase.getInstance("https://ifit-mobile-app-default-rtdb.firebaseio.com/").getReference("calisthenics");

        // Add the hard-coded calisthenics workouts to the database
        calisthenicsRef.child("Pull-Ups").setValue(new Calisthenics(
                "Pull-Ups",
                "30 seconds",
                R.drawable.cal1,
                "Grab the pullup bar with your palms down (shoulder-width grip)\nHang to the pullup-bar with straight arms and your legs off the floor\nPull yourself up by pulling your elbows down to the floor\nGo all the way up until your chin passes the be bar\nLower yourself until your arms are straight",
                3,
                10
        ));

        calisthenicsRef.child("Dead Hang").setValue(new Calisthenics(
                "Dead Hang",
                "1 minute",
                R.drawable.cal2,
                "Use a secure overhead bar. Step or bench to reach the bar. Hang with straight arms for 10-60 seconds.",
                3,
                1
        ));

        calisthenicsRef.child("Side Plank Hang").setValue(new Calisthenics(
                "Side Plank Hang",
                "45 seconds",
                R.drawable.cal3,
                "Lie on your side with your legs straight and feet stacked. Prop your upper body up on your elbow and forearm. Engage your core and lift your hips off the floor, forming a straight line from your head to your feet. Hold for 30-60 seconds, then switch sides.",
                2,
                1
        ));

        calisthenicsRef.child("Push-Ups").setValue(new Calisthenics(
                "Push-Ups",
                "30-60 seconds",
                R.drawable.cal4,
                "Start in a high plank position with your hands shoulder-width apart and your body in a straight line .\nLower your chest towards the floor, keeping your core engaged.\nPush back up to the starting position by straightening your arms.",
                3,
                10
        ));
        calisthenicsRef.child("Walking Lunge").setValue(new Calisthenics(
                "Walking Lunge",
                "45 seconds",
                R.drawable.cal5,
                "Stand with feet together.  \n" +
                "Take a big step forward with one leg." +
                "Lower your back knee toward the ground, keeping your leg straight."+
                "Push back up, bringing your feet together. "+
                "Repeat, alternating legs with each step.  ", 3,12));
        calisthenicsRef.child("Hanging L Seats").setValue(new Calisthenics(
                "Hanging L Seats",
                "15 seconds",
                R.drawable.cal6,
                "Start in a hanging position on a pull-up bar.\n" +
                        "Lift your legs up to form an \"L\" shape, keeping them straight.\n" +
                        "Hold the L-seat position, engaging your core.\n" +
                        "Slowly lower your legs back to the hanging position.\n ",3, 10));
        calisthenicsRef.child("Upper Back Stretching").setValue(new Calisthenics(
                "Upper Back Stretching",
                "30-60 seconds",
                R.drawable.cal7,
                "Lie face down on mat/ground, arms extended in front.\n" +
                        "Engage your upper back muscles and lift your arms and legs off the ground.\n" +
                        "Hold the stretch for a few seconds, keeping your back extended.\n" +
                        "Slowly lower back down to the starting position.\n",

                3,
                10
        ));
    }


}



   // private List<Calisthenics> generateAllCalisthenics() {
//        List<Calisthenics> allCalisthenics = new ArrayList<>();
//
//        // Add all calisthenics here
//        allCalisthenics.add(new Calisthenics("Pull-Ups", "30 seconds", R.drawable.cal1, "Grab the pullup bar with your palms down (shoulder-width grip)\nHang to the pullup-bar with straight arms and your legs off the floor\nPull yourself up by pulling your elbows down to the floor\nGo all the way up until your chin passes the be bar\nLower yourself until your arms are straight", 3, 10));
//        allCalisthenics.add(new Calisthenics("Dead Hang", "1 minute", R.drawable.cal2,
//                "Use a secure overhead bar. Step or bench to reach the bar. Hang with straight arms for 10-60 seconds.", 3, 1));
//        allCalisthenics.add(new Calisthenics("Side Plank Hang", "45 seconds", R.drawable.cal3,
//                "Lie on your side with your legs straight and feet stacked. Prop your upper body up on your elbow and forearm. Engage your core and lift your hips off the floor, forming a straight line from your head to your feet. Hold for 30-60 seconds, then switch sides.", 2, 1));
//        allCalisthenics.add(new Calisthenics("Push-Ups", "30-60 seconds", R.drawable.cal4,
//                "Start in a high plank position with your hands shoulder-width apart and your body in a straight line .\n" +
//                        "Lower your chest towards the floor, keeping your core engaged.\n" +
//                        "Push back up to the starting position by straightening your arms.\n", 3, 10));
//
//        return allCalisthenics;
//    }