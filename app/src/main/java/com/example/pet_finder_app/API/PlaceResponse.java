package com.example.pet_finder_app.API;


import java.util.List;

public class PlaceResponse {
    private List<Predictions> predictions ;

    public List<Predictions> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Predictions> predictions) {
        this.predictions = predictions;
    }

    public class Predictions {
        private String description;

        private String place_id ;
        private String reference;
        private Structured_Formatting structured_formatting;

        //"executed_time": 61,
        //"executed_time_all": 63,
        //"status": "OK"
        private int executed_time ;
        private int executed_time_all;
        private String status ;
        private List<Terms> terms;

        public List<Terms> getTerms() {
            return terms;
        }

        public void setTerms(List<Terms> terms) {
            this.terms = terms;
        }

        public String getPlace_id() {
            return place_id;
        }

        public String getStatus() {
            return status;
        }

        public String getDescription() {
            return description;
        }

        public String getReference() {
            return reference;
        }

        public int getExecuted_time() {
            return executed_time;
        }

        public int getExecuted_time_all() {
            return executed_time_all;
        }



        public Structured_Formatting getStructured_formatting() {
            return structured_formatting;
        }
    }
    public class Structured_Formatting {
        private String main_text ;
        private String secondary_text;

        public String getSecondary_text() {
            return secondary_text;
        }

        public String getMain_text() {
            return main_text;
        }
    }
    public class Terms {
        private int offset ;
        private String value ;

        public int getOffset() {
            return offset;
        }

        public String getValue() {
            return value;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
