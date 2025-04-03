package com.vos_initiales.projet_integrateur;

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

    private EditText nom, prenom, courriel,password, url;
    private Button btnSuivant, btnPrecedant;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Liaison avec les vues XML
        nom = findViewById(R.id.champs_nom1);
        prenom = findViewById(R.id.champs_prenom1);
        courriel = findViewById(R.id.champs_courriel1);
        password = findViewById(R.id.motDePasseInscription);
        url = findViewById(R.id.url_cv);

        btnSuivant = findViewById(R.id.btnSuivant);
        btnPrecedant = findViewById(R.id.btnPrecedant);

        // Bouton précédent -> retour au profil
        btnPrecedant.setOnClickListener(v -> startActivity(new Intent(UpdateProfile.this, ProfilE.class)));

        // Bouton suivant -> mise à jour du profil
        btnSuivant.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateProfile.this, Swiping.class);
            startActivity(intent);
        });

    }

    private void miseAJour() {
        String sNom = nom.getText().toString().trim();
        String sPrenom = prenom.getText().toString().trim();
        String sCourriel = courriel.getText().toString().trim();
        String sMdp = password.getText().toString().trim();
        String sUrl = url.getText().toString().trim();

        if (sNom.isEmpty() || sPrenom.isEmpty() || sCourriel.isEmpty()|| sUrl.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Etudiant etudiant = new Etudiant(sNom, sPrenom, sCourriel, sMdp,sUrl);
        Call<Void> call = api.updateProfile(etudiant);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateProfile.this, "Mise à jour réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateProfile.this, Swiping.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateProfile.this, "Échec de la mise à jour", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
