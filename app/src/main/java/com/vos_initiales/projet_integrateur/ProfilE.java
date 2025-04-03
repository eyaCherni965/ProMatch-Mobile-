package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilE extends AppCompatActivity {
    private static final int UPDATE_REQUEST_CODE = 1; // Code pour identifier le retour de mise à jour


    private TextView tvNom, tvPrenom, tvCourriel;
    private Button btnMiseJour, btnExplorerStage, btnEtatDemande, btnVoirCV;
    private String cvUrl; // Pour stocker l'URL du CV

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        // 1. INITIALISATION DES VUES
        tvNom = findViewById(R.id.profil_nom);
        tvPrenom = findViewById(R.id.profil_prenom);
        tvCourriel = findViewById(R.id.profil_courriel);
        btnMiseJour = findViewById(R.id.profil_update);
        btnExplorerStage = findViewById(R.id.profile_stages);
        btnEtatDemande = findViewById(R.id.profile_etat);
        btnVoirCV = findViewById(R.id.profile_cv);

        // 2. AFFICHAGE DES DONNEES
        Intent intent = getIntent();
        tvNom.setText(intent.getStringExtra("etudiant_nom")); // Affiche le nom
        tvPrenom.setText(intent.getStringExtra("etudiant_prenom")); // Affiche le prénom
        tvCourriel.setText(intent.getStringExtra("etudiant_email")); // Affiche l'email
        cvUrl = intent.getStringExtra("etudiant_url"); // Stocke l'URL CV

        // 3. GESTION DES INTERACTIONS
        // Bouton Mise à jour - Ouvre UpdateProfile avec les données actuelles
        btnMiseJour.setOnClickListener(v -> {
            Intent updateIntent = new Intent(ProfilE.this, UpdateProfile.class);
            updateIntent.putExtra("etudiant_id", intent.getIntExtra("etudiant_id", -1));
            updateIntent.putExtra("etudiant_nom", tvNom.getText().toString());
            updateIntent.putExtra("etudiant_prenom", tvPrenom.getText().toString());
            updateIntent.putExtra("etudiant_email", tvCourriel.getText().toString());
            updateIntent.putExtra("etudiant_url", cvUrl);
            startActivityForResult(updateIntent, UPDATE_REQUEST_CODE); // On attend un résultat
        });

        // Bouton Voir CV - Ouvre l'URL dans un navigateur
        btnVoirCV.setOnClickListener(v -> {
            if (cvUrl != null && !cvUrl.isEmpty()) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cvUrl)));
            } else {
                Toast.makeText(this, "Aucun CV disponible", Toast.LENGTH_SHORT).show();
            }
        });

        // Boutons autres fonctionnalités
        btnExplorerStage.setOnClickListener(v ->
                startActivity(new Intent(this, Swiping.class)));

        btnEtatDemande.setOnClickListener(v ->
                startActivity(new Intent(this, EtatDeMesDemandes.class)));
    }

    // GESTION DU RETOUR DE UPDATE PROFILE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Si c'est bien notre requête de mise à jour et que c'est un succès
        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Mise à jour des TextViews avec les nouvelles valeurs
            tvNom.setText(data.getStringExtra("updated_nom"));
            tvPrenom.setText(data.getStringExtra("updated_prenom"));
            tvCourriel.setText(data.getStringExtra("updated_email"));
            cvUrl = data.getStringExtra("updated_url"); // Mise à jour de l'URL
        }
    }

}