package com.example.androoidprojectclass;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androoidprojectclass.Activities.HomePageActivity;
import com.example.androoidprojectclass.Activities.SignInActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    TextView main_LBL_AppName;
    MaterialTextView main_LBL_welcome;
    ImageView main_IMG_logo;
    MaterialButton main_BTN_ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViews();



        main_BTN_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SignInActivity when "I'm Ready" button is clicked
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        main_BTN_ready = findViewById(R.id.main_BTN_ready);
        main_IMG_logo = findViewById(R.id.main_IMG_logo);
        main_LBL_AppName = findViewById(R.id.main_LBL_AppName);
        main_LBL_welcome = findViewById(R.id.main_LBL_welcome);
    }
}