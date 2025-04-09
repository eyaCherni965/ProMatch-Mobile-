package com.vos_initiales.projet_integrateur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    private EditText etNom, etPrenom, etCourriel, etUrlCV;
    private Button btnPrecedent, btnSuivant;
    private int etudiantId; // Pour stocker l'ID de l'étudiant à mettre à jour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // 1. INITIALISATION DES VUES

        etNom = findViewById(R.id.champs_nom1);
        etPrenom = findViewById(R.id.champs_prenom1);
        etCourriel = findViewById(R.id.champs_courriel1);
        etUrlCV = findViewById(R.id.url_cv1);
        btnPrecedent = findViewById(R.id.btnPrecedant);
        btnSuivant = findViewById(R.id.btnSuivant);

        // 2. RECUPERATION DES DONNEES

        Intent intent = getIntent();
        etudiantId = intent.getIntExtra("etudiant_id", -1);
        // Pré-remplissage des champs
        etNom.setText(intent.getStringExtra("etudiant_nom"));
        etPrenom.setText(intent.getStringExtra("etudiant_prenom"));
        etCourriel.setText(intent.getStringExtra("etudiant_email"));
        etUrlCV.setText(intent.getStringExtra("etudiant_url"));

        // 3. GESTION DES BOUTONS
        btnPrecedent.setOnClickListener(v -> finish()); // Retour simple sans sauvegarde

        btnSuivant.setOnClickListener(v -> {
            if (validateInputs()) {  // Validation d'abord
                updateProfile();    // Ensuite mise à jour
            }
        });
    }

    // VALIDATION DES CHAMPS
    private boolean validateInputs() {
        // Vérifie que les champs obligatoires sont remplis
        if (etNom.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Le nom est obligatoire", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPrenom.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Le prénom est obligatoire", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etCourriel.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "L'email est obligatoire", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true; // Tous les champs valides
    }

    // MISE A JOUR DU PROFIL
    private void updateProfile() {
        // Création de l'objet Etudiant avec les nouvelles valeurs
        Etudiant etudiant = new Etudiant(
                etNom.getText().toString().trim(),
                etPrenom.getText().toString().trim(),
                etCourriel.getText().toString().trim(),
                "", // Mot de passe laissé vide (non modifié)
                etUrlCV.getText().toString().trim()
        );
        etudiant.setId_etudiant(etudiantId); // ID  pour l'update API

        // Appel API !!!!!il manque la création de l'api
        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        api.updateProfile(etudiant).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si succès, on renvoie les nouvelles données à ProfilE
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updated_nom", etudiant.getNom());
                    resultIntent.putExtra("updated_prenom", etudiant.getPrenom());
                    resultIntent.putExtra("updated_email", etudiant.getEmail());
                    resultIntent.putExtra("updated_url", etudiant.getUrl());
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Ferme l'activité
                } else {
                    Toast.makeText(UpdateProfile.this, "Échec de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}



