package com.example.pet_finder_app.Class;

public class    Pet {
    private String idPet, name, description, gender, color, size,typeId, categoryId;
    private int age ;
    private float  weight;

    public Pet() {
    }

    public Pet(String idPet,String name, String description, String gender, int age,  String color, String size,float  weight, String typeId, String categoryId){
    this.idPet = idPet;
    this.name= name;
    this.description=description;
    this.gender = gender;
    this.age = age ;
    this.color = color;
    this.size = size;
    this.weight = weight;
    this.typeId = typeId;
    this.categoryId= categoryId;
    }

    public String getIdPet() {
        return idPet;
    }

    public float getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}
