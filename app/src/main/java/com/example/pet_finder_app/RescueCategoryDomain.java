package com.example.pet_finder_app;

public class RescueCategoryDomain {
    private int image_id;
    private String name, address, distance,place_id,geocode;

    public RescueCategoryDomain() {
    }

    public RescueCategoryDomain(int image_id, String name, String address, String distance, String place_id, String geocode) {
        this.image_id = image_id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.place_id = place_id;
        this.geocode = geocode;

    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
