package com.example.pet_finder_app;

public class AdoptingCategoryDomain {
    private int image_id, gender, favorite, status, width, height;
    private String name, location;

    public AdoptingCategoryDomain() {
    }

    public AdoptingCategoryDomain(int image_id, String name, int favorite, String location, int gender, int status) {
        this.image_id = image_id;
        this.name = name;
        this.favorite = favorite;
        this.location = location;
        this.gender = gender;
        this.status = status;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
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
}
