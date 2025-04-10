package com.vos_initiales.projet_integrateur;

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

public class PageConnexion extends AppCompatActivity {

    EditText etEmail, etMdp;
    Button btnBack, btnConnexion;

    private static final String TAG = "API_CONNEXION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

        etEmail = findViewById(R.id.adresseCourriel);
        etMdp = findViewById(R.id.motDePasse);
        btnBack = findViewById(R.id.buttonBack);
        btnConnexion = findViewById(R.id.buttonConnecter);

        btnBack.setOnClickListener(v -> startActivity(new Intent(PageConnexion.this, Filtre.class)));

        btnConnexion.setOnClickListener(v -> verifierConnexion());
    }

    private void verifierConnexion() {
        String email = etEmail.getText().toString().trim();
        String mdp = etMdp.getText().toString().trim();

        if (email.isEmpty() || mdp.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Etudiant etudiant = new Etudiant();
        etudiant.setEmail(email);
        etudiant.setMdp(mdp);

        Log.d(TAG, "Tentative de connexion avec : " + email + " / " + mdp);

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<Etudiant> call = api.connexionEtudiant(etudiant);

        call.enqueue(new Callback<Etudiant>() {
            @Override
            public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                Log.d(TAG, "Code HTTP: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    Etudiant compte = response.body();

                    Log.d(TAG, "Connexion réussie : " + compte.toString());

                    Toast.makeText(PageConnexion.this, "Bienvenue " + compte.getPrenom(), Toast.LENGTH_LONG).show();

                    // Envoyer les données à ProfilE
                    Intent intent = new Intent(PageConnexion.this, ProfilE.class);
                    intent.putExtra("etudiant_id", compte.getId_etudiant());
                    intent.putExtra("etudiant_nom", compte.getNom());
                    intent.putExtra("etudiant_prenom", compte.getPrenom());
                    intent.putExtra("etudiant_email", compte.getEmail());
                    intent.putExtra("etudiant_url", compte.getUrl());
                    startActivity(intent);
                    finish();

                } else {
                    Log.e(TAG, "Échec de connexion. Corps réponse nul ou code non 200.");
                    Toast.makeText(PageConnexion.this, "Identifiants incorrects", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Etudiant> call, Throwable t) {
                Log.e(TAG, "Erreur réseau/API : " + t.getMessage(), t);
                Toast.makeText(PageConnexion.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
