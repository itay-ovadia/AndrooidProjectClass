package com.example.androoidprojectclass.Models;

public class Yoga {
    private String name;
    private int duration;
    private int imageResource;
    private String instructions;

    public Yoga() {
        // No-argument constructor required by Firebase Realtime Database
    }
    public Yoga(String name, int duration, int imageResource) {
        this.name = name;
        this.duration = duration;
        this.imageResource = imageResource;
    }

    public Yoga(String name, int duration, int imageResource, String instructions) {
        this.name = name;
        this.duration = duration;
        this.imageResource = imageResource;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
