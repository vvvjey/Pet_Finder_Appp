package com.example.pet_finder_app.API;

public class DistrictPlaces {
    private String district_id;
    private String district_name;
    private String district_type;

    public String getDistrict_type() {
        return district_type;
    }

    public void setDistrict_type(String district_type) {
        this.district_type = district_type;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }
}
