package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface EtudiantAPI {

    @GET("/etudiants") // à adapter selon ton endpoint Express
    Call<List<Etudiant>> getEtudiants();

    @POST("/etudiants")
    Call<Etudiant> ajouterEtudiant(@Body Etudiant etudiant);
}

