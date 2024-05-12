package com.example.pet_finder_app;

import java.io.Serializable;

public class AdoptingPetItem implements Serializable {
    private String age;
    private String categoryId;
    private String color;
    private String gender;
    private String idPet;
    private String imgUrl;
    private String name;
    private String registerDate;
    private String status;

    public AdoptingPetItem(String age, String categoryId, String color, String gender, String idPet,String imgUrl, String name, String registerDate, String status) {
        this.age = age;
        this.categoryId = categoryId;
        this.color = color;
        this.gender = gender;
        this.idPet = idPet;
        this.imgUrl = imgUrl;
        this.name = name;
        this.registerDate = registerDate;
        this.status = status;
    }


    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public AdoptingPetItem(){}
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
