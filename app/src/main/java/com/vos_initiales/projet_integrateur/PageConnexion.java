package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PageConnexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

        // Bouton Étudiant
        Button btnBack = findViewById(R.id.buttonBack);

        btnBack.setOnClickListener(v -> startActivity(new Intent(PageConnexion.this, ChoixEtudiant.class)));


    }}