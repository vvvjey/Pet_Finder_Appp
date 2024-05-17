package com.example.pet_finder_app.Class;

import java.lang.reflect.Array;

public class FavouritePet {
    private String userId ;
    private Array petIdList[];
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPetIdList(Array[] petIdList) {
        this.petIdList = petIdList;
    }

    public Array[] getPetIdList() {
        return petIdList;
    }
}
