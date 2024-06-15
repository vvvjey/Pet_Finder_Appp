package com.example.pet_finder_app.Class;

import java.util.ArrayList;
import java.util.List;

public class FavoritePet {
    private String idUser;
    private List<String> favoritePet = new ArrayList<>();

    public FavoritePet(String idUser, List<String> favoritePet) {
        this.idUser = idUser;
        this.favoritePet = favoritePet;
    }
    public  FavoritePet(){}

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<String> getFavoritePet() {
        return favoritePet;
    }

    public void setFavoritePet(List<String> favoritePet) {
        this.favoritePet = favoritePet;
    }
}
