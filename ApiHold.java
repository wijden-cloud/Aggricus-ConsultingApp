package com.example.consultingapp.Controller;

import com.example.consultingapp.Model.Annonce;
import com.example.consultingapp.Model.Consigne;
import com.example.consultingapp.Model.Partenaire;
import com.example.consultingapp.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiHold {
    @POST("login")
    Call<User> login(@Body User u);
    @PATCH("utilisateurs/{password}")
    Call<User> updatePassword(@Path("password") String password , @Body User u);
    @POST("utilisateurs")
    Call<User> signin(@Body User u);
    @PATCH("utilisateurs/{subs}")
    Call<User> updateSubscription(@Path("subs") String subs , @Body User u);
    @GET("utilisateurs/{id}/")
    Call <User> getUserInfo(@Path("id") String UserID);




    @POST("annonces")
    Call<Annonce> createAnnonce(@Body Annonce a);
    @GET("annonces")
    Call <List<Annonce>>allAnnonces();
    @POST("annonces/{id}/")
    Call <List<Annonce>>userAnnonces(@Path("id") String Id);
    @GET("annonces/{id}/")
    Call <Annonce> annonceByID(@Path("id") String Id);
    @PATCH("annonces/{id}/")
    Call <Annonce> updateAnnonce(@Path("id") String Id,Annonce a);
    @DELETE("annonces/{id}/")
    Call <Annonce> deleteAnnonce(@Path("id") String Id);
    @PUT("annonces/{categorie}/")
    Call <List<Annonce>>annoncesByCath(@Path("categorie") String Id);

    @GET("consignes/{cat}/")
    Call <List<Consigne>>consigneByCategorie(@Path("cat") String cat);
    @GET("consignes")
    Call <List<Consigne>>consignes();

    @GET("partenaires")
    Call <List<Partenaire>>allPartners();





}
