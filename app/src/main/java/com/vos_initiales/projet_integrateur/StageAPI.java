package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface StageAPI {

    @GET("/stages")
    Call<List<Stage>> getStages();

    @GET("/stages")
    Call<List<Stage>> getFilteredStages(
            @Query("domaine") String domaine,
            @Query("salaire") String salaire,
            @Query("duree") String duree
    );

    @POST("candidature/statut")
    Call<Void> postCandidature(@Body Candidature candidature);


}

