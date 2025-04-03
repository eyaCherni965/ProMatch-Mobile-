package com.vos_initiales.projet_integrateur;

public class Stage {
    private int id;
    private String titre;
    private String description;
    private String entreprise;
    private String lieu;

    public Stage(int id, String titre, String description, String entreprise, String lieu) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.entreprise = entreprise;
        this.lieu = lieu;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEntreprise() { return entreprise; }
    public void setEntreprise(String entreprise) { this.entreprise = entreprise; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
}

