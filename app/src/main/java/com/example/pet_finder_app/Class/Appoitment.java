package com.example.pet_finder_app.Class;

public class Appoitment {
    private String date, idAppointment, petId ,receiverId, senderId, timeType;

    public Appoitment(){

    }

    public Appoitment(String date, String idAppointment, String petId, String receiverId, String senderId, String timeType) {
        this.date = date;
        this.idAppointment = idAppointment;
        this.petId = petId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.timeType = timeType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(String idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
}
