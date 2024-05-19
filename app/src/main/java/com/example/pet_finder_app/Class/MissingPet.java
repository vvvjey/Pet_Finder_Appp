package com.example.pet_finder_app.Class;

public class MissingPet extends Pet{
    private String id, idPet,typeMissing, addressMissing, dateMissing,detailDescription;
    public MissingPet(){

    }
    public MissingPet(String age, String categoryId, String color, String description, String gender, String idPet, String imgUrl, String name, String registerDate, String size, String typeId, String weight, String userPostId, String id, String typeMissing, String addressMissing, String dateMissing, String detailDescription) {
        super(age, categoryId, color, description, gender, idPet, imgUrl, name, registerDate, size, typeId, weight, userPostId);
        this.id = id;
        this.typeMissing = typeMissing;
        this.addressMissing = addressMissing;
        this.dateMissing = dateMissing;
        this.detailDescription = detailDescription;
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
