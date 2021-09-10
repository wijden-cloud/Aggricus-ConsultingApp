package com.example.consultingapp.Model;

import com.google.gson.annotations.SerializedName;

public class Consigne {
    private String title,description,categorie,creationDate;
    @SerializedName("_id")
    private String id;

    public Consigne(String title, String description, String categorie, String creationDate, String id) {
        this.title = title;
        this.description = description;
        this.categorie = categorie;
        this.creationDate = creationDate;
        this.id = id;
    }

    public Consigne(String categorie) {
        this.categorie = categorie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
