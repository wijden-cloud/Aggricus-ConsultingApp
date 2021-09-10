package com.example.consultingapp.Model;

import com.google.gson.annotations.SerializedName;

public class Partenaire {
    private String name,address,type,tel,creationDate,description,email;
    @SerializedName("_id")
    private String id;

    public Partenaire(String name, String address, String type, String tel, String creationDate, String description, String email, String id) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.tel = tel;
        this.creationDate = creationDate;
        this.id = id;
        this.description = description;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddess(String addess) {
        this.address = addess;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
