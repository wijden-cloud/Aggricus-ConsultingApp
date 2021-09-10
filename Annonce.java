package com.example.consultingapp.Model;

import com.google.gson.annotations.SerializedName;

public class Annonce {
    private String title,description,categorie,userID,creationDate;
    @SerializedName("_id")
    private String id;
    public Annonce(String title, String description, String categorie, String userID) {
        this.title = title;
        this.description = description;
        this.categorie = categorie;
        this.userID = userID;
    }

    public Annonce(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Annonce(String userID) {
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
