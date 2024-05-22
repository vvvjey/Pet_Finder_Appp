package com.example.pet_finder_app.API;
import android.inputmethodservice.Keyboard;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class Result {

    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Rows> rows ;
    private String status ;
    public void setDestination_addresses(List<String> destination_addresses){
        this.destination_addresses =destination_addresses;
    }

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }




    public class Rows {
        public List<Elements> element;

        public List<Elements> getElement() {
            return element;
        }

        public void setElement(List<Elements> element) {
            this.element = element;
        }
    }
    public class Elements {
        private Distance distance;
        private Duration duration;

        private String  status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }

    public class Distance {


        private String text;

        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Duration {


        private String text;

        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

