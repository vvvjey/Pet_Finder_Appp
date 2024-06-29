package com.example.pet_finder_app.Class;

public class User {
    private String address,dateBirth,email, gender,name,phoneNumber, roleId,userId, imgUser;

    public User(){

    }

    public User(String address, String dateBirth, String email, String gender, String name, String phoneNumber, String roleId, String userId, String imgUser) {
        this.address = address;
        this.dateBirth = dateBirth;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.userId = userId;
        this.imgUser = imgUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
