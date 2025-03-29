package com.vos_initiales.projet_integrateur;

import android.annotation.SuppressLint;
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button connexion = findViewById(R.id.buttonConnecter);

        btnBack.setOnClickListener(v -> startActivity(new Intent(PageConnexion.this, ChoixEtudiant.class)));

        //en vrai quand on sera rendu à l'api, quand la personne rentre ses informations, on doit aller dans la base de donné si les informations sont corrects
        connexion.setOnClickListener(v -> startActivity(new Intent(PageConnexion.this, PageInscription.class)));

    }}