package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilE extends AppCompatActivity {

    private TextView tvNom, tvPrenom, tvCourriel;
    private Button btnMiseJour, btnExplorerStage, btnEtatDemande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        // Récupérer les données envoyées depuis PageConnexion
        Intent intent = getIntent();
        String nom = intent.getStringExtra("etudiant_nom");
        String prenom = intent.getStringExtra("etudiant_prenom");
        String email = intent.getStringExtra("etudiant_email");

        // Lier les TextView aux IDs dans le layout
        tvNom = findViewById(R.id.profil_nom);
        tvPrenom = findViewById(R.id.profil_prenom);
        tvCourriel = findViewById(R.id.profil_courriel);

        // Afficher les données
        tvNom.setText("Nom : " + nom);
        tvPrenom.setText("Prénom : " + prenom);
        tvCourriel.setText("Email : " + email);

        // Lier les boutons
        btnMiseJour = findViewById(R.id.profil_update);
        btnExplorerStage = findViewById(R.id.profile_stages);
        btnEtatDemande = findViewById(R.id.profile_etat); // attention, c’est bien celui-là

        // Actions des boutons
        btnMiseJour.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, UpdateProfile.class)));
        btnExplorerStage.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, Swiping.class)));
        btnEtatDemande.setOnClickListener(v -> startActivity(new Intent(ProfilE.this, EtatDeMesDemandes.class)));
    }
}
