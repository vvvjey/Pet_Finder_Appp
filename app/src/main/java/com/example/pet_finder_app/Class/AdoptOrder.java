package com.example.pet_finder_app.Class;

public class AdoptOrder {

        private String idAdopt, idPet, userId;
        public AdoptOrder() {
        }

        public AdoptOrder(String idAdopt, String idPet, String userId) {
            this.idAdopt = idAdopt;
            this.idPet = idPet;
            this.userId = userId;
        }

    public void setIdAdopt(String idAdopt) {
        this.idAdopt = idAdopt;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdAdopt() {
        return idAdopt;
    }

    public String getIdPet() {
        return idPet;
    }

    public String getUserId() {
        return userId;
    }
}

