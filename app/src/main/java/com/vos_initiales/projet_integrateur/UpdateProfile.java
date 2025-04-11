package com.vos_initiales.projet_integrateur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    private EditText etNom, etPrenom, etCourriel, etUrlCV;
    private int etudiantId;
    private Button btnPrecedent, btnSuivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialisation des champs de texte
        etNom = findViewById(R.id.champs_nom1);
        etPrenom = findViewById(R.id.champs_prenom1);
        etCourriel = findViewById(R.id.champs_courriel1);
        etUrlCV = findViewById(R.id.url_cv1);

        // Initialisation des boutons
        btnPrecedent = findViewById(R.id.btnPrecedant);
        btnSuivant = findViewById(R.id.btnSuivant);

        // Récupération des données
        Intent intent = getIntent();
        etudiantId = intent.getIntExtra("etudiant_id", -1);
        etNom.setText(intent.getStringExtra("etudiant_nom"));
        etPrenom.setText(intent.getStringExtra("etudiant_prenom"));
        etCourriel.setText(intent.getStringExtra("etudiant_email"));
        etUrlCV.setText(intent.getStringExtra("etudiant_url"));

        // Configuration des listeners
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Bouton Précédent
        btnPrecedent.setOnClickListener(v -> {
            // Retour à l'activité précédente sans sauvegarder
            finish();
        });

        // Bouton Suivant (Valider)
        btnSuivant.setOnClickListener(v -> {
            if (validateInputs()) {
                updateProfile();
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (etNom.getText().toString().trim().isEmpty()) {
            etNom.setError("Nom obligatoire");
            isValid = false;
        }
        if (etPrenom.getText().toString().trim().isEmpty()) {
            etPrenom.setError("Prénom obligatoire");
            isValid = false;
        }
        if (etCourriel.getText().toString().trim().isEmpty()) {
            etCourriel.setError("Courriel obligatoire");
            isValid = false;
        }

        return isValid;
    }

    private void updateProfile() {
        Etudiant etudiant = new Etudiant();
        etudiant.setId_etudiant(etudiantId);
        etudiant.setNom(etNom.getText().toString().trim());
        etudiant.setPrenom(etPrenom.getText().toString().trim());
        etudiant.setEmail(etCourriel.getText().toString().trim());
        etudiant.setUrl(etUrlCV.getText().toString().trim());


        Log.d("UPDATE_DEBUG", "Données à envoyer: " + etudiant.toString());

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        api.updateProfilEtudiant(etudiant).enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                if (response.isSuccessful()) {
                    Log.d("UPDATE_DEBUG", "Mise à jour réussie");
                    Toast.makeText(UpdateProfile.this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();

                    // Retour à ProfilE avec résultat OK
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Log.e("UPDATE_DEBUG", "Échec: " + response.code());
                    Toast.makeText(UpdateProfile.this, "Échec de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Log.e("UPDATE_DEBUG", "Erreur réseau: ", t);
                Toast.makeText(UpdateProfile.this, "Erreur réseau", Toast.LENGTH_SHORT).show();

            }
        });
    }
}