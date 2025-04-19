package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilE extends AppCompatActivity {
    private static final int UPDATE_REQUEST_CODE = 1;
    private TextView tvNom, tvPrenom, tvCourriel;
    private Button btnMiseJour, btnVoirCV, btnDemandes,btnStage;
    private String cvUrl;
    private int etudiantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        //Debug: Vérifie l'Intent reçu
        Log.d("PROFIL_DEBUG", "Intent reçu: " + getIntent().getExtras());

        // Initialisation des vues
        tvNom = findViewById(R.id.profil_nom);
        tvPrenom = findViewById(R.id.profil_prenom);
        tvCourriel = findViewById(R.id.profil_courriel);
        btnMiseJour = findViewById(R.id.profil_update);
        btnVoirCV = findViewById(R.id.profile_cv);
        btnDemandes = findViewById(R.id.profile_etat);
        btnStage= findViewById(R.id.profile_stages);


        // Configuration des listeners
        setupButtonListeners();

        // Affiche d'abord les données locales
        displayInitialData(getIntent());

        // Puis charge les données fraîches
        etudiantId = getIntent().getIntExtra("etudiant_id", -1);
        if (etudiantId != -1) {
            chargerProfil();
        } else {
            finish();
        }

        btnVoirCV.setOnClickListener(v -> {
            if (cvUrl != null && !cvUrl.isEmpty()) {
                Intent intent = new Intent(ProfilE.this, VoirCV.class);
                intent.putExtra("url_cv", cvUrl);
                startActivity(intent);
            } else {
                Toast.makeText(ProfilE.this, "Aucun CV disponible", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupButtonListeners() {
        // Bouton Voir CV
        btnVoirCV.setOnClickListener(v -> {
            if (cvUrl != null && !cvUrl.isEmpty()) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cvUrl));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "Impossible d'ouvrir le lien", Toast.LENGTH_SHORT).show();
                    Log.e("PROFIL_DEBUG", "Erreur ouverture CV: " + e.getMessage());
                }
            } else {
                Toast.makeText(this, "Aucun CV disponible", Toast.LENGTH_SHORT).show();
            }
        });

        // Bouton Mise à jour
        btnMiseJour.setOnClickListener(v -> {
            Intent updateIntent = new Intent(ProfilE.this, UpdateProfile.class);
            updateIntent.putExtra("etudiant_id", etudiantId);
            updateIntent.putExtra("etudiant_nom", tvNom.getText().toString());
            updateIntent.putExtra("etudiant_prenom", tvPrenom.getText().toString());
            updateIntent.putExtra("etudiant_email", tvCourriel.getText().toString());
            updateIntent.putExtra("etudiant_url", cvUrl);
            startActivityForResult(updateIntent, UPDATE_REQUEST_CODE);
        });

        // Bouton État des demandes
        btnDemandes.setOnClickListener(v -> {
            Intent demandesIntent = new Intent(ProfilE.this, EtatDeMesDemandes.class);
            startActivity(demandesIntent);
        });

        // Bouton pour aller filtrer stage
        btnStage.setOnClickListener(v -> {
            Intent stageIntent = new Intent(ProfilE.this,Filtre.class);
            startActivity(stageIntent);
        });

        Button btnLikedStages = findViewById(R.id.btn_liked_stages);
        btnLikedStages.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilE.this, LikedStagesActivity.class);
            startActivity(intent);
        });


    }


private void displayInitialData(Intent intent) {
        // Affiche les données locales immédiatement
        if (intent != null) {
            tvNom.setText(intent.getStringExtra("etudiant_nom"));
            tvPrenom.setText(intent.getStringExtra("etudiant_prenom"));
            tvCourriel.setText(intent.getStringExtra("etudiant_email"));
            cvUrl = intent.getStringExtra("etudiant_url");

            Log.d("PROFIL_DEBUG", "Données locales affichées");
        }
    }

    private void chargerProfil() {
        Log.d("PROFIL_DEBUG", "Tentative de chargement pour ID: " + etudiantId);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        // Loguez le token pour vérifier qu'il n'est pas vide
        Log.d("PROFIL_DEBUG", "Token utilisé: " + (token.isEmpty() ? "VIDE" : "NON VIDE"));

        if (token.isEmpty()) {
            Toast.makeText(this, "Veuillez vous reconnecter", Toast.LENGTH_SHORT).show();
            // Rediriger vers la connexion
            startActivity(new Intent(ProfilE.this, PageConnexion.class));
            finish();
            return;
        }

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        api.getProfilEtudiant("Bearer " + token).enqueue(new Callback<Etudiant>(){
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Etudiant etudiant = response.body();
                    Log.d("PROFIL_DEBUG", "Données reçues: " + etudiant.toString());

                    tvNom.setText(etudiant.getNom());
                    tvPrenom.setText(etudiant.getPrenom());
                    tvCourriel.setText(etudiant.getEmail());
                    cvUrl = etudiant.getUrl();
                } else {
                    Log.e("PROFIL_DEBUG", "Erreur: " + response.code() + " - " + response.message());
                    Toast.makeText(ProfilE.this, "Profil introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Log.e("PROFIL_DEBUG", "Erreur réseau: ", t);
                Toast.makeText(ProfilE.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            chargerProfil(); // Rafraîchit les données après mise à jour
        }
    }
}