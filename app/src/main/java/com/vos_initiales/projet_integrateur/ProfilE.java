package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilE extends AppCompatActivity {

    private static final int UPDATE_REQUEST_CODE = 1;

    private TextView tvNom, tvPrenom, tvCourriel;
    private Button btnMiseJour, btnExplorerStage, btnEtatDemande, btnVoirCV;
    private String cvUrl;
    private int etudiantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        // 1. Initialisation des vues
        tvNom = findViewById(R.id.profil_nom);
        tvPrenom = findViewById(R.id.profil_prenom);
        tvCourriel = findViewById(R.id.profil_courriel);
        btnMiseJour = findViewById(R.id.profil_update);
        btnExplorerStage = findViewById(R.id.profile_stages);
        btnEtatDemande = findViewById(R.id.profile_etat);
        btnVoirCV = findViewById(R.id.profile_cv);

        // 2. Récupération de l'ID depuis l'intent
        Intent intent = getIntent();
        etudiantId = intent.getIntExtra("etudiant_id", -1);

        // 3. Chargement des données depuis l'API
        if (etudiantId != -1) {
            chargerProfil(etudiantId);
        } else {
            Toast.makeText(this, "Erreur : ID étudiant manquant", Toast.LENGTH_SHORT).show();
        }
    }

    private void chargerProfil(int id) {
        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<List<Etudiant>> call = api.profilEtudiant(id);

        call.enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Etudiant etudiant = response.body().get(0);
                    afficherProfil(etudiant);
                    configurerBoutons(etudiant);
                } else {
                    Toast.makeText(ProfilE.this, "Impossible de charger le profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                Toast.makeText(ProfilE.this, "Erreur API : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void afficherProfil(Etudiant etudiant) {
        tvNom.setText(etudiant.getNom());
        tvPrenom.setText(etudiant.getPrenom());
        tvCourriel.setText(etudiant.getEmail());
        cvUrl = etudiant.getUrl();
    }

    private void configurerBoutons(Etudiant etudiant) {
        btnMiseJour.setOnClickListener(v -> {
            Intent updateIntent = new Intent(ProfilE.this, UpdateProfile.class);
            updateIntent.putExtra("etudiant_id", etudiant.getId_etudiant());
            updateIntent.putExtra("etudiant_nom", etudiant.getNom());
            updateIntent.putExtra("etudiant_prenom", etudiant.getPrenom());
            updateIntent.putExtra("etudiant_email", etudiant.getEmail());
            updateIntent.putExtra("etudiant_url", etudiant.getUrl());
            startActivityForResult(updateIntent, UPDATE_REQUEST_CODE);
        });

        btnVoirCV.setOnClickListener(v -> {
            if (cvUrl != null && !cvUrl.isEmpty()) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cvUrl)));
            } else {
                Toast.makeText(this, "Aucun CV disponible", Toast.LENGTH_SHORT).show();
            }
        });

        btnExplorerStage.setOnClickListener(v ->
                startActivity(new Intent(this, SwipingActivity.class)));

        btnEtatDemande.setOnClickListener(v ->
                startActivity(new Intent(this, EtatDeMesDemandes.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            tvNom.setText(data.getStringExtra("updated_nom"));
            tvPrenom.setText(data.getStringExtra("updated_prenom"));
            tvCourriel.setText(data.getStringExtra("updated_email"));
            cvUrl = data.getStringExtra("updated_url");
        }
    }
}
