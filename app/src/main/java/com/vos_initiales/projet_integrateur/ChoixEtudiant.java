package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoixEtudiant extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_etudiant);

        // Bouton Étudiant
        Button btnConnexion = findViewById(R.id.buttonConnexion);
        Button btnInscription = findViewById(R.id.buttonInscription);
        Button btnBack0 = findViewById(R.id.buttonBack0);


        btnConnexion .setOnClickListener(v -> startActivity(new Intent(ChoixEtudiant.this, PageConnexion.class)));

        btnInscription .setOnClickListener(v -> startActivity(new Intent(ChoixEtudiant.this, PageInscription.class)));

        btnBack0.setOnClickListener(v -> startActivity(new Intent(ChoixEtudiant.this, MainActivity.class)));
        }}