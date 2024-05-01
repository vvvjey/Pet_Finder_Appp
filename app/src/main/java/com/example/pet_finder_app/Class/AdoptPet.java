package com.example.pet_finder_app.Class;

public class AdoptPet {
    private String id, idPet;
    private float price;


    public AdoptPet() {
    }

    public AdoptPet(String id, String  idPet, float price) {
        this.id=id;
        this.idPet =idPet;
        this.price= price ;

    }

    public String getIdPet() {
        return idPet;
    }

    public float getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}