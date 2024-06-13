package com.example.pet_finder_app;

public class AdoptingCategoryDomain {
    private String image_id, gender, favorite, status, idPet, statusOrder;
    private String name, location, breed, date_adopt, ranking, condition;
    private String age;
    private String price;

    public AdoptingCategoryDomain(String imgUrl, String name, Object o, String price, String genderUrl, String categoryId, String age, String id, String status) {
    }

//    public AdoptingCategoryDomain(String image_id, String name, String favorite, String location, String gender, String status) {
//        this.image_id = image_id;
//        this.name = name;
//        this.favorite = favorite;
//        this.location = location;
//        this.gender = gender;
//        this.status = status;
//    }

    public AdoptingCategoryDomain(String idPet, String image_id, String name, String favorite, String price, String gender, String breed, String age, String condition) {
        this.idPet = idPet;
        this.image_id = image_id;
        this.name = name;
        this.favorite = favorite;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.condition = condition;
    }

    public AdoptingCategoryDomain(String image_id, String name, String price, String gender, String breed, String age) {
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
    }
    public AdoptingCategoryDomain(String image_id, String name, String date_adopt, String gender, String breed, String age, String location) {
        this.image_id = image_id;
        this.name = name;
        this.date_adopt = date_adopt;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.location = location;
    }
    public AdoptingCategoryDomain(String idPet, String image_id, String name, String price, String gender, String breed, String age, String condition) {
        this.idPet = idPet;
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.condition = condition;
    }
    public AdoptingCategoryDomain(String idPet, String image_id, String name, String price, String gender, String breed, String age, String date_adopt, String ranking, String condition, String statusOrder) {
        this.idPet = idPet;
        this.image_id = image_id;
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
        this.date_adopt = date_adopt;
        this.ranking = ranking;
        this.condition = condition;
        this.statusOrder = statusOrder;
    }


    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate_adopt() {
        return date_adopt;
    }

    public void setDate_adopt(String date_adopt) {
        this.date_adopt = date_adopt;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
