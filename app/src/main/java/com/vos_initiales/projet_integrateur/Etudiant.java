package com.vos_initiales.projet_integrateur;

import com.google.gson.annotations.Expose;

public class Etudiant {

    @Expose(serialize = false, deserialize = true)
    private int id_etudiant;

    @Expose(serialize = false, deserialize = true)
    private String nom;

    @Expose(serialize = false, deserialize = true)
    private String prenom;

    @Expose
    private String email;

    @Expose
    private String mdp;

    public Etudiant() {}

    public Etudiant(int id_etudiant, String nom, String prenom, String email, String mdp) {
        this.id_etudiant = id_etudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
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
}
