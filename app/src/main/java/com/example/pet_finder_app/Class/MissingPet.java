package com.example.pet_finder_app.Class;

import java.util.List;

public class MissingPet extends Pet{
    private String id, idPet,typeMissing, addressMissing, dateMissing,requestPosterMissing,statusMissing;
    public MissingPet(){

    }
    public MissingPet(String age, String breed, String categoryId, String color, String description, String gender, String idPet, List<String> imgUrl, String name, String registerDate, String size, String typeId, String weight, String id, String typeMissing, String addressMissing, String dateMissing, String requestPosterMissing, String postUserId, String statusMissing) {
        super(age,breed, categoryId, color, description, gender, idPet, imgUrl, name, registerDate, size, typeId, weight,postUserId);
        this.id = id;
        this.typeMissing = typeMissing;
        this.addressMissing = addressMissing;
        this.dateMissing = dateMissing;
        this.requestPosterMissing = requestPosterMissing;
        this.statusMissing = statusMissing;
    }


    public String getId() {
        return id;
    }

    public String getAddressMissing() {
        return addressMissing;
    }

    public String getDateMissing() {
        return dateMissing;
    }

    public String getRequestPosterMissing() {
        return requestPosterMissing;
    }

    public String getTypeMissing() {
        return typeMissing;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getStatusMissing() {
        return statusMissing;
    }

    public void setStatusMissing(String statusMissing) {
        this.statusMissing = statusMissing;
    }


    public void setAddressMissing(String addressMissing) {
        this.addressMissing = addressMissing;
    }

    public void setDateMissing(String dateMissing) {
        this.dateMissing = dateMissing;
    }

    public void setRequestPosterMissing(String requestPosterMissing) {
        this.requestPosterMissing = requestPosterMissing;
    }

    public void setTypeMissing(String typeMissing) {
        this.typeMissing = typeMissing;
    }
}
