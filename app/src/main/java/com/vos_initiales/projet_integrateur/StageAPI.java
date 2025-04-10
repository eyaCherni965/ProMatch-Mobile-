package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StageAPI {

    @GET("/stages")
    Call<List<Stage>> getStages();

    @GET("/stages")
    Call<List<Stage>> getFilteredStages(
            @Query("domaine") String domaine,
            @Query("salaire") String salaire,
            @Query("duree") String duree
    );

}

