package com.example.pet_finder_app.API;

public class PlaceDetailResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private String formatted_address;
        private Geometry geometry;

       private String place_id ;

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getPlace_id() {
            return place_id;
        }

        public String getFormattedAddress() {
            return formatted_address;
        }

        public void setFormattedAddress(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }



    public static class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }
    public static class Location {
        private double lat; // Latitude as a double
        private double lng; // Longitude as a double

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

}
