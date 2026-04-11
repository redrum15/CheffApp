package com.chefapp.models;

public class Recipe {
    private String name;
    private String cuisine;
    private int timeMinutes;
    private String difficulty;
    private float rating;
    private String description;
    private int imageResId;

    public Recipe(String name, String cuisine, int timeMinutes, String difficulty, float rating, String description, int imageResId) {
        this.name = name;
        this.cuisine = cuisine;
        this.timeMinutes = timeMinutes;
        this.difficulty = difficulty;
        this.rating = rating;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public int getTimeMinutes() { return timeMinutes; }
    public String getDifficulty() { return difficulty; }
    public float getRating() { return rating; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }

    public String getInfoLine() {
        return cuisine + " · " + timeMinutes + " min · " + difficulty;
    }
}
