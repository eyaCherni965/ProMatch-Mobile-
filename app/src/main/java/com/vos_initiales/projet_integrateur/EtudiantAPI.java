package com.vos_initiales.projet_integrateur;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EtudiantAPI {

    @POST("/inscription")
    Call<Void> inscription(@Body Etudiant etudiant);

    @POST("/connexionEtudiant")
    Call<Etudiant> connexionEtudiant(@Body Etudiant etudiant);

}
