package com.vos_initiales.projet_integrateur;

import com.google.gson.annotations.SerializedName;

public class ConnexionResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Etudiant user;

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public Etudiant getUser() { return user; }
}

