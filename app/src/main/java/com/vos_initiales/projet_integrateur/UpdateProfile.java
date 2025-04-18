package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.content.SharedPreferences;
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

        // Initialisation des vues
        etNom = findViewById(R.id.champs_nom1);
        etPrenom = findViewById(R.id.champs_prenom1);
        etCourriel = findViewById(R.id.champs_courriel1);
        etUrlCV = findViewById(R.id.url_cv1);

        btnPrecedent = findViewById(R.id.btnPrecedant);
        btnSuivant = findViewById(R.id.btnSuivant);

        // Récupération des données de l'étudiant
        Intent intent = getIntent();
        etudiantId = intent.getIntExtra("etudiant_id", -1);
        etNom.setText(intent.getStringExtra("etudiant_nom"));
        etPrenom.setText(intent.getStringExtra("etudiant_prenom"));
        etCourriel.setText(intent.getStringExtra("etudiant_email"));
        etUrlCV.setText(intent.getStringExtra("etudiant_url"));

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        btnPrecedent.setOnClickListener(v -> finish());

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

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Session expirée. Veuillez vous reconnecter.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PageConnexion.class));
            finish();
            return;
        }

        Log.d("UPDATE_DEBUG", "Envoi du profil avec token");

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<Void> call = api.updateProfilEtudiant("Bearer " + token, etudiant);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("UPDATE_DEBUG", "Profil mis à jour !");
                    Toast.makeText(UpdateProfile.this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Log.e("UPDATE_DEBUG", "Échec HTTP: " + response.code());
                    Toast.makeText(UpdateProfile.this, "Erreur de mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("UPDATE_DEBUG", "Erreur réseau: ", t);
                Toast.makeText(UpdateProfile.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
