package com.example.pet_finder_app.Class;

public class Role {
    private String roleId, name;
    public Role(){}

    public Role(String roleId, String name){
        this.roleId=roleId;
        this.name=name;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
