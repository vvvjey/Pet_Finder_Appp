package com.example.pet_finder_app.Class;

public class MissingPet {
    private String id, idPet,typeMissing, addressMissing, dateMissing,detailDescription;
    public MissingPet(){

    }
    public MissingPet(String id, String idPet,String typeMissing, String addressMissing, String dateMissing,String detailDescription){
        this.id = id;
        this.idPet = idPet;
        this.typeMissing= typeMissing;
        this.addressMissing= addressMissing;
        this.dateMissing= dateMissing;
        this.detailDescription=detailDescription;

    }

    public String getIdPet() {
        return idPet;
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

    public String getDetailDescription() {
        return detailDescription;
    }

    public String getTypeMissing() {
        return typeMissing;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public void setAddressMissing(String addressMissing) {
        this.addressMissing = addressMissing;
    }

    public void setDateMissing(String dateMissing) {
        this.dateMissing = dateMissing;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public void setTypeMissing(String typeMissing) {
        this.typeMissing = typeMissing;
    }
}
