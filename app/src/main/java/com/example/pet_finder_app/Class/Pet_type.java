package com.example.pet_finder_app.Class;

public class Pet_type {
    private String typeId, name ;
    public Pet_type(){}
    public Pet_type(String typeId, String name){
        this.typeId = typeId;
        this.name  = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
