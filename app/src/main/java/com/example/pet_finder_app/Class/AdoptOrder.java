package com.example.pet_finder_app.Class;

import java.util.List;

public class AdoptOrder {

        private String idOrder, idPet, userId, requestMsg, status;
        private List<String> statusTime;
        private List<String> imageUrl;
        public AdoptOrder() {
        }

        public AdoptOrder(String idAdopt, String idPet, String userId, String requestMsg, String status, List<String> statusTime, List<String> imageUrl) {
            this.idOrder = idAdopt;
            this.idPet = idPet;
            this.userId = userId;
            this.requestMsg = requestMsg;
            this.status = status;
            this.statusTime = statusTime;
            this.imageUrl = imageUrl;
        }

    public List<String> getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(List<String> statusTime) {
        this.statusTime = statusTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public void setIdOrder(String idAdopt) {
        this.idOrder = idAdopt;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdPet() {
        return idPet;
    }

    public String getUserId() {
        return userId;
    }
}

