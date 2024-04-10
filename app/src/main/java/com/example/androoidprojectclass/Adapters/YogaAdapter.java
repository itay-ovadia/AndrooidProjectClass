package com.example.androoidprojectclass.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androoidprojectclass.Activities.YogaDetailsActivity;
import com.example.androoidprojectclass.Models.Yoga;
import com.example.androoidprojectclass.R;

import java.util.List;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.YogaViewHolder> {

    private List<Yoga> yogaList;
    private OnImageClickListener onImageClickListener;

    public YogaAdapter(List<Yoga> yogaList) {
        this.yogaList = yogaList;
    }

    @NonNull
    @Override
    public YogaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_yoga_workout_item, parent, false);
        return new YogaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaViewHolder holder, int position) {
        Yoga yoga = yogaList.get(position);

        // Bind data to views in the ViewHolder
        holder.yogaTitle.setText(yoga.getName());
        holder.yogaImage.setImageResource(yoga.getImageResource());

        // Set click listener on the workout item
        holder.itemView.setOnClickListener(v -> {
            openYogaDetailsActivity(yoga, v.getContext());
        });
    }

    @Override
    public int getItemCount() {
        return yogaList.size();
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    public interface OnImageClickListener {
        void onImageClick(Yoga yoga);
    }

    private void openYogaDetailsActivity(Yoga yoga, android.content.Context context) {
        Intent intent = new Intent(context, YogaDetailsActivity.class);
        intent.putExtra("workoutName", yoga.getName());
        intent.putExtra("workoutImage", yoga.getImageResource());
        intent.putExtra("workoutInstructions", yoga.getInstructions());
        intent.putExtra("workoutDuration", yoga.getDuration());
        context.startActivity(intent);
    }

    public static class YogaViewHolder extends RecyclerView.ViewHolder {
        ImageView yogaImage;
        TextView yogaTitle;

        public YogaViewHolder(@NonNull View itemView) {
            super(itemView);
            yogaImage = itemView.findViewById(R.id.yoga_item_image);
            yogaTitle = itemView.findViewById(R.id.yoga_item_title);
        }
    }
}