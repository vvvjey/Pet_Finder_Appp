package com.example.pet_finder_app;

public class AdoptStatusItem {

        private String image_id;
        private String name, size, breed, date, color, idPet, statusOrder, idOrder, idAdopt;
        public AdoptStatusItem() {
        }

        public AdoptStatusItem(String image_id, String name, String size, String breed, String color, String statusOrder, String idPet, String idOrder, String idAdopt) {
            this.image_id = image_id;
            this.name = name;
            this.size = size;
            this.breed = breed;
            this.color = color;
            this.statusOrder = statusOrder;
            this.idPet = idPet;
            this.idOrder = idOrder;
            this.idAdopt = idAdopt;
        }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdAdopt() {
        return idAdopt;
    }

    public void setIdAdopt(String idAdopt) {
        this.idAdopt = idAdopt;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getBreed() {
            return breed;
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

    }
