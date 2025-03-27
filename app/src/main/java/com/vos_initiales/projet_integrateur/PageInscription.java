package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PageInscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        // Bouton Étudiant
        Button btnBack = findViewById(R.id.inscription_retour);

        btnBack.setOnClickListener(v -> startActivity(new Intent(PageInscription.this, ChoixEtudiant.class)));


    }}