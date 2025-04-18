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
        Call<ConnexionResponse> call = api.connexionEtudiant(etudiant);

        call.enqueue(new Callback<ConnexionResponse>() {
            @Override
            public void onResponse(Call<ConnexionResponse> call, Response<ConnexionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ConnexionResponse connexion = response.body();
                    Etudiant compte = connexion.getUser();  // ICI tu récupères bien le user
                    compte.setToken(connexion.getToken()); // on rattache le token manuellement

                    // Sauvegarde du token
                    SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", connexion.getToken());
                    editor.apply();

                    Toast.makeText(PageConnexion.this, "Bienvenue " + compte.getPrenom(), Toast.LENGTH_LONG).show();

                    // Envoyer les infos à ProfilE
                    Intent intent = new Intent(PageConnexion.this, ProfilE.class);
                    intent.putExtra("etudiant_id", compte.getId_etudiant());
                    intent.putExtra("etudiant_nom", compte.getNom());
                    intent.putExtra("etudiant_prenom", compte.getPrenom());
                    intent.putExtra("etudiant_email", compte.getEmail());
                    intent.putExtra("etudiant_url", compte.getUrl());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PageConnexion.this, "Identifiants incorrects", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ConnexionResponse> call, Throwable t) {
                Toast.makeText(PageConnexion.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
