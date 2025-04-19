package com.vos_initiales.projet_integrateur;

public class Candidature {
    private int id_etudiant;
    private int id_stage;
    private String statut;

    public Candidature(int id_etudiant, int id_stage, String statut) {
        this.id_etudiant = id_etudiant;
        this.id_stage = id_stage;
        this.statut = statut;
    }

    public int getId_etudiant() {
        return id_etudiant;
    }

    public int getId_stage() {
        return id_stage;
    }

    public String getStatut() {
        return statut;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public void setId_stage(int id_stage) {
        this.id_stage = id_stage;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}

