package com.example.pet_finder_app;

public class AppointmentItem {
    private String appointmentId,idOrder, imageId, idPet, name, gender, date, time, sender;

    public AppointmentItem(String appointmentId, String idOrder, String imageId, String idPet, String name, String gender, String date, String time, String sender) {
        this.appointmentId = appointmentId;
        this.imageId = imageId;
        this.idOrder = idOrder;
        this.idPet = idPet;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.time = time;
        this.sender = sender;
    }

    public AppointmentItem(){}

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
