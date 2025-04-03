package com.vos_initiales.projet_integrateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StageAPI {

    @GET("/stages") // ← assure-toi que c’est bien le bon endpoint
    Call<List<Stage>> getStages();
}

