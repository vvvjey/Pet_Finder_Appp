package com.example.pet_finder_app.Class;

import java.util.List;

public class Pet {
    private String age;
    private String breed;
    private String categoryId;
    private String color;
    private String description;
    private String gender;
    private String idPet;
    private List<String> imgUrl;
    private String name;
    private String registerDate;
    private String size;
    private String typeId;
    private String weight;
    private String postUserId;
    public Pet(String age,String breed, String categoryId, String color, String description, String gender, String idPet, List<String> imgUrl, String name, String registerDate, String size, String typeId, String weight,String postUserId) {
        this.age = age;
        this.breed = breed;
        this.categoryId = categoryId;
        this.color = color;
        this.description = description;
        this.gender = gender;
        this.idPet = idPet;
        this.imgUrl = imgUrl;
        this.name = name;
        this.registerDate = registerDate;
        this.size = size;
        this.typeId = typeId;
        this.weight = weight;
        this.postUserId = postUserId;
    }

    public Pet(){}
    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {

        this.postUserId = postUserId;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
