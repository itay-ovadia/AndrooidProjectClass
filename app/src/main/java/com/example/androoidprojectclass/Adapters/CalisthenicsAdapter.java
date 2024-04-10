package com.example.androoidprojectclass.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androoidprojectclass.Activities.CalisthenicsDetailsActivity;
import com.example.androoidprojectclass.Models.Calisthenics;
import com.example.androoidprojectclass.R;

import java.util.List;

public class CalisthenicsAdapter extends RecyclerView.Adapter<CalisthenicsAdapter.CalisthenicsViewHolder> {
    private List<Calisthenics> calisthenicsList;

    public CalisthenicsAdapter(List<Calisthenics> calisthenicsList) {
        this.calisthenicsList = calisthenicsList;
    }

    @NonNull
    @Override
    public CalisthenicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_calisthenics_workout_item, parent, false);
        return new CalisthenicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalisthenicsViewHolder holder, int position) {
        Calisthenics calisthenics = calisthenicsList.get(position);

        // Bind data to views in the ViewHolder
        holder.calisthenicsImage.setImageResource(calisthenics.getImageResource());
        holder.calisthenicsTitle.setText(calisthenics.getName());
        holder.calisthenisDuration.setText(calisthenics.getDuration());

        // Set click listener on the workout item
        holder.itemView.setOnClickListener(v -> {
            // Pass the workout data to the CalisthenicsDetailsActivity
            Intent intent = new Intent(v.getContext(), CalisthenicsDetailsActivity.class);
            intent.putExtra("workoutName", calisthenics.getName());
            intent.putExtra("workoutImage", calisthenics.getImageResource());
            intent.putExtra("workoutInstructions", calisthenics.getInstructions());
            intent.putExtra("workoutSets", calisthenics.getSets());
            intent.putExtra("workoutReps", calisthenics.getReps());
            intent.putExtra("workoutDuration", calisthenics.getDuration());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return calisthenicsList.size();
    }

    public static class CalisthenicsViewHolder extends RecyclerView.ViewHolder {
        ImageView calisthenicsImage;
        TextView calisthenicsTitle;
        TextView calisthenisDuration;

        public CalisthenicsViewHolder(@NonNull View itemView) {
            super(itemView);
            calisthenicsImage = itemView.findViewById(R.id.calisthenics_item_image);
            calisthenicsTitle = itemView.findViewById(R.id.calisthenics_item_title);
            calisthenisDuration = itemView.findViewById(R.id.calisthenics_item_duration);
        }
    }
}