package com.example.pet_finder_app.Class;


public class MissingPetContact {
    private String name,idPet,description,fromUserId,posterUserId,message,time;
    public MissingPetContact() {
    }
    public MissingPetContact(String name,String idPet,String description, String fromUserId, String posterUserId, String message, String time) {
        this.name = name;
        this.idPet = idPet;
        this.description = description;
        this.fromUserId = fromUserId;
        this.posterUserId = posterUserId;
        this.message = message;
        this.time = time;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getPosterUserId() {
        return posterUserId;
    }

    public void setPosterUserId(String posterUserId) {
        this.posterUserId = posterUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
