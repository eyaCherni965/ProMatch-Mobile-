package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EtudiantAPI {

    @POST("/inscriptionEtudiant")
    Call<Void> inscription(@Body Etudiant etudiant);

    @POST("/connexionEtudiant")
    Call<Etudiant> connexionEtudiant(@Body Etudiant etudiant);

    @POST("/updateEtudiant")
    Call<Void> updateProfile(@Body Etudiant etudiant);

/// RAJOUTER API UPDATE



}
