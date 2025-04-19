package com.vos_initiales.projet_integrateur;

import com.google.gson.annotations.SerializedName;

public class Demande {

    @SerializedName("nom_poste")
    private String nomPoste;

    @SerializedName("statut")
    private String statut;

    public String getNomPoste() {
        return nomPoste;
    }

    public String getStatut() {
        return statut;
    }
}
