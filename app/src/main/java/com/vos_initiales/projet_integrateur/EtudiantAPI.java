package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;

import retrofit2.http.Path;

public interface EtudiantAPI {

    @POST("/inscriptionEtudiant")
    Call<Void> inscription(@Body Etudiant etudiant);

    @GET("profilEtudiant")
    Call<Etudiant> getProfilEtudiant(@Header("Authorization") String token);

    @POST("profilEtudiant")
    Call<Void> updateProfilEtudiant(@Header("Authorization") String token, @Body Etudiant etudiant);

    @POST("/connexionEtudiant")
    Call<ConnexionResponse> connexionEtudiant(@Body Etudiant etudiant);

}
