package com.example.pet_finder_app.Class;

public class User {
    private String userId,name,email,phoneNumber, role, gender, roleId, address;
    private int age ;

    public User(){

    }
    public User(String userId,String name,String email,String phoneNumber,  int age , String role, String gender, String roleId, String address){
    this.userId =userId;
    this.name   =name;
    this.email=email;
    this.phoneNumber =phoneNumber;
    this.age= age;
    this.role = role;
    this.gender =gender;
    this.roleId=roleId;
    this.address=address;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
