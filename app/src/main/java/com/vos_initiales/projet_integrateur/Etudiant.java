package com.vos_initiales.projet_integrateur;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Etudiant {

    @Expose(serialize = true, deserialize = true)
    private int id_etudiant;

    @Expose
    private String nom;

    @Expose
    private String prenom;

    @Expose
    private String email;

    @Expose
    private String mdp;

    @SerializedName("url_cv")
    @Expose
    private String url;

    private String token;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email, String mdp, String url) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.url = url;
    }

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
