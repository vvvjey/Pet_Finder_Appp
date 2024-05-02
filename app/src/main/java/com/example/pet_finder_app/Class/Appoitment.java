package com.example.pet_finder_app.Class;

public class Appoitment {
    private String idAppointment ,receiverId, senderId, timeType, petId,date ;

    public Appoitment(){

    }
    public Appoitment(String idAppointment ,String receiverId, String senderId, String timeType, String petId,String date ){
        this.idAppointment=idAppointment;
        this.receiverId=receiverId;
        this.senderId=senderId;
        this.timeType=timeType;
        this.petId=petId;
        this.date  = date ;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdAppointment(String idAppointment) {
        this.idAppointment = idAppointment;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getDate() {
        return date;
    }

    public String getIdAppointment() {
        return idAppointment;
    }

    public String getPetId() {
        return petId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getTimeType() {
        return timeType;
    }

}
