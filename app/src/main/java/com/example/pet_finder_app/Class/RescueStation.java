package com.example.pet_finder_app.Class;

public class RescueStation {
    private String idStation, name, address,description, distance ;
    public RescueStation(){}
    public RescueStation(String idStation, String name, String address,String description, String distance){
        this.idStation=idStation;
        this.name=name;
        this.address=address;
        this.description=description;

    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIdStation() {
        return idStation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }
}
