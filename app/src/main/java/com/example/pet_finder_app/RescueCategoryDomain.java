package com.example.pet_finder_app;

public class RescueCategoryDomain {
    private int image_id;
    private String name, location, distance;

    public RescueCategoryDomain() {
    }

    public RescueCategoryDomain(int image_id, String name, String location, String distance) {
        this.image_id = image_id;
        this.name = name;
        this.location = location;
        this.distance = distance;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
