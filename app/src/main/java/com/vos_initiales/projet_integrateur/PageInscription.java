package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageInscription extends AppCompatActivity {

    private EditText nom, prenom, courriel,password;
    private EditText url;
    private Button btnBack, btnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        // Liaison avec les vues XML
        nom = findViewById(R.id.nomInscription);
        prenom = findViewById(R.id.prenomInscription);
        courriel = findViewById(R.id.emailInscription);
        url = findViewById(R.id.url_cv);
        password = findViewById(R.id.motDePasseInscription);
        btnBack = findViewById(R.id.inscriptionRetour);
        btnInscription = findViewById(R.id.buttonInscription);

        // Bouton retour
        btnBack.setOnClickListener(v -> startActivity(new Intent(PageInscription.this, ChoixEtudiant.class)));

        // Bouton inscription -> appelle l'API
        btnInscription.setOnClickListener(v -> inscrireEtudiant());
    }

    private void inscrireEtudiant() {
        String sNom = nom.getText().toString().trim();
        String sPrenom = prenom.getText().toString().trim();
        String sCourriel = courriel.getText().toString().trim();
        String sMdp = password.getText().toString().trim();
        String sUrl = url.getText().toString().trim();

        if (sNom.isEmpty() || sPrenom.isEmpty() || sCourriel.isEmpty() || sMdp.isEmpty() || sUrl.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Etudiant etudiant = new Etudiant(sNom, sPrenom, sCourriel, sMdp,sUrl);

        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<Void> call = api.inscription(etudiant);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PageInscription.this, "Inscription réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PageInscription.this, PageConnexion.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PageInscription.this, "Échec de l'inscription (code: " + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PageInscription.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
