package com.example.pet_finder_app.API;
import java.util.List;

public class GeoResponse {

    private PlusCode plus_code;
    private List<GeocoderResult> results;

    public PlusCode getPlusCode() {
        return plus_code;
    }

    public void setPlusCode(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public List<GeocoderResult> getResults() {
        return results;
    }

    public void setResults(List<GeocoderResult> results) {
        this.results = results;
    }

    public static class PlusCode {

        private String compound_code;
        private String global_code;

        public String getCompoundCode() {
            return compound_code;
        }

        public void setCompoundCode(String compound_code) {
            this.compound_code = compound_code;
        }

        public String getGlobalCode() {
            return global_code;
        }

        public void setGlobalCode(String global_code) {
            this.global_code = global_code;
        }
    }

    public static class GeocoderResult {

        private List<AddressComponent> address_components;
        private String formatted_address;
        private Geometry geometry;
        private String place_id;
        private String reference;
        private List<String> types;

        public List<AddressComponent> getAddressComponents() {
            return address_components;
        }

        public void setAddressComponents(List<AddressComponent> address_components) {
            this.address_components = address_components;
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

        public String getPlaceId() {
            return place_id;
        }

        public void setPlaceId(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public static class AddressComponent {

        private String long_name;
        private String short_name;

        public String getLongName() {
            return long_name;
        }

        public void setLongName(String long_name) {
            this.long_name = long_name;
        }

        public String getShortName() {
            return short_name;
        }

        public void setShortName(String short_name) {
            this.short_name = short_name;
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

        private double lat;
        private double lng;

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
