package com.example.pet_finder_app.Class;

public class AdoptPet {
    private String addressAdopting;
    private String id;
    private String idPet;
    private String price;
    private String status;

    public AdoptPet() {
    }

    public AdoptPet(String addressAdopting, String id, String idPet, String price, String status) {
        this.addressAdopting = addressAdopting;
        this.id = id;
        this.idPet = idPet;
        this.price = price;
        this.status = status;
    }

    public String getAddressAdopting() {
        return addressAdopting;
    }

    public void setAddressAdopting(String addressAdopting) {
        this.addressAdopting = addressAdopting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
