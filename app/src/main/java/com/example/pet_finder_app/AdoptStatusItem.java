package com.example.pet_finder_app;

public class AdoptStatusItem {

        private int image_id;
        private String name, size, breed, date, color;
        public AdoptStatusItem() {
        }

        public AdoptStatusItem(int image_id, String name, String size, String breed, String date, String color) {
            this.image_id = image_id;
            this.name = name;
            this.size = size;
            this.breed = breed;
            this.date = date;
            this.color = color;
        }
        public int getImage_id() {
            return image_id;
        }

        public void setImage_id(int image_id) {
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
