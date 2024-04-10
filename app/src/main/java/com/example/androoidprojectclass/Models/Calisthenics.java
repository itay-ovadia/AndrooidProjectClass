package com.example.androoidprojectclass.Models;

public class Calisthenics {
    private String name;
    private String duration;
    private int imageResource;
    private String instructions;
    private int sets;
    private int reps;

    public Calisthenics() {
        // No-argument constructor required by Firebase Realtime Database
    }
    public Calisthenics(String name, String duration, int imageResource, String instructions, int sets, int reps) {
        this.name = name;
        this.duration = duration;
        this.imageResource = imageResource;
        this.instructions = instructions;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}