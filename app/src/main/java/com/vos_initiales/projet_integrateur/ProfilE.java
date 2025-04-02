package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Parameter;

public class ProfilE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        Button btnMiseJour =findViewById(R.id.profil_update);
        Button btnexplorerStage = findViewById(R.id.profile_stages);
        Button btnEtatdemande = findViewById(R.id.profile_stages);

        //bouton mise a jours profile
        btnMiseJour.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, UpdateProfile.class)));
        //bouton  explorer stages
        btnMiseJour.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, Swiping.class)));
        //bouton etat de demandes
        btnMiseJour.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, EtatDeMesDemandes.class)));




    }
}