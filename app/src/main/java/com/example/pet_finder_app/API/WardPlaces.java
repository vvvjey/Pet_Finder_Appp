package com.example.pet_finder_app.API;

public class WardPlaces {
    private String ward_id;
    private String ward_name;
    private String ward_type;

    public WardPlaces(String wardId, String wardName, String wardType) {
        ward_id = wardId;
        ward_name = wardName;
        ward_type = wardType;
    }

    public String getWard_type() {
        return ward_type;
    }

    public void setWard_type(String ward_type) {
        this.ward_type = ward_type;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }
}
