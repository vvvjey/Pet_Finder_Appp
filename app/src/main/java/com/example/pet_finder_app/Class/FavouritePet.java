package com.example.pet_finder_app.Class;

import java.lang.reflect.Array;

public class FavouritePet {
    private String userId ;
    private String[] petIdList;

    public FavouritePet(String uid, String[] petid){
        this.userId = uid;
        this.petIdList = petid;
    }
    public FavouritePet(){

    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPetIdList(String[] petIdList) {
        this.petIdList = petIdList;
    }

    public String[] getPetIdList() {
        return petIdList;
    }
}
