package com.example.pet_finder_app;

public class AdoptingPetItem {
    private int image_id, gender;
    private String name, status, appearance, date;
    private int age;

    public AdoptingPetItem() {
    }

    public AdoptingPetItem(int image_id, String name, String status, String appearance, String date, int gender, int age) {
        this.image_id = image_id;
        this.name = name;
        this.status = status;
        this.appearance = appearance;
        this.date = date;
        this.gender = gender;
        this.age = age;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
