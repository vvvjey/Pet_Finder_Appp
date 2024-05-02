package com.example.pet_finder_app.Class;

public class Pet_category {
    private String categoryId, name ;
    public Pet_category(){

    }
    public Pet_category(String categoryId, String name ){
        this.categoryId =categoryId;
        this.name   = name;

    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
