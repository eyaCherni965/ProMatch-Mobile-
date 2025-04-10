package com.vos_initiales.projet_integrateur;

import com.google.gson.annotations.SerializedName;

public class Stage {
    private int id_stage;
    @SerializedName("compagnie")
    private String compagnie;

    @SerializedName("coordinateur")
    private String coordinateur;

    @SerializedName("nom_departement")
    private String nom_departement;

    @SerializedName("nom_poste")
    private String nom_poste;

    @SerializedName("duree")
    private int duree;

    @SerializedName("desc_poste")
    private String desc_poste;

    @SerializedName("taux_horaire")
    private double taux_horaire;

    @SerializedName("adresse")
    private String adresse;

    @SerializedName("courriel")
    private String courriel;

    @SerializedName("url_image")
    private String url_image;

    @SerializedName("id_employeur")
    private int id_employeur;

    public int getId_stage() {
        return id_stage;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public String getCoordinateur() {
        return coordinateur;
    }

    public String getNom_departement() {
        return nom_departement;
    }

    public String getNom_poste() {
        return nom_poste;
    }

    public int getDuree() {
        return duree;
    }

    public String getDesc_poste() {
        return desc_poste;
    }

    public double getTaux_horaire() {
        return taux_horaire;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCourriel() {
        return courriel;
    }

    public String getUrl_image() {
        return url_image;
    }

    public int getId_employeur() {
        return id_employeur;
    }
}
