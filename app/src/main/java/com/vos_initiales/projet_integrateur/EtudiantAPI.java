package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EtudiantAPI {

    @POST("/inscriptionEtudiant")
    Call<Void> inscription(@Body Etudiant etudiant);

    @POST("/connexionEtudiant")
    Call<Etudiant> connexionEtudiant(@Body Etudiant etudiant);



        @GET("etudiants/{id_etudiant}")
        Call<Etudiant> getProfilEtudiant(@Path("id_etudiant") int id_etudiant);

        @POST("etudiants/update")
        Call<Etudiant> updateProfilEtudiant(@Body Etudiant etudiant);  // Retourne l'étudiant mis à jour
    }
