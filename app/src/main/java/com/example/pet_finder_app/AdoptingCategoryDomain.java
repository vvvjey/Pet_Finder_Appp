package com.example.pet_finder_app;

public class AdoptingCategoryDomain {
    private int image_id, gender, favorite, status;
    private String name, location, breed, date_adopt, ranking, condition;
    private int age;
    private float price;

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

    public AdoptingCategoryDomain(int image_id, String name, int favorite, float price, int gender, String breed, int age) {
        this.image_id = image_id;
        this.name = name;
        this.favorite = favorite;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
    }

    public AdoptingCategoryDomain(int image_id, String name, float price, int gender, String breed, int age) {
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
    }

    public AdoptingCategoryDomain(int image_id, String name, float price, int gender, String breed, int age, String date_adopt, String ranking, String condition) {
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.date_adopt = date_adopt;
        this.ranking = ranking;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate_adopt() {
        return date_adopt;
    }

    public void setDate_adopt(String date_adopt) {
        this.date_adopt = date_adopt;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(int gender) {
        this.gender = gender;
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
