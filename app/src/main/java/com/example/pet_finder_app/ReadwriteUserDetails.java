package com.example.pet_finder_app;

public class ReadwriteUserDetails {
    public String fullname, email, password;
    public ReadwriteUserDetails(String textFullName, String textEmail, String textPassword){
        this.fullname = textFullName;
        this.email = textEmail;
        this.password = textPassword;
    }
}
