package com.vos_initiales.projet_integrateur;

public class SessionManager {
    private static int idEtudiant = -1;

    public static void setIdEtudiant(int id) {
        idEtudiant = id;
    }

    public static int getIdEtudiant() {
        return idEtudiant;
    }
}
