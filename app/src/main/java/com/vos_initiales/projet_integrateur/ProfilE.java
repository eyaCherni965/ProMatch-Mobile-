package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "ProfilE";

    private TextView tvNom, tvPrenom, tvCourriel;
    private Button btnMiseJour, btnExplorerStage, btnEtatDemande, btnVoirCV;
    private String cvUrl;
    private int etudiantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_e);

        tvNom = findViewById(R.id.profil_nom);
        tvPrenom = findViewById(R.id.profil_prenom);
        tvCourriel = findViewById(R.id.profil_courriel);
        btnMiseJour = findViewById(R.id.profil_update);
        btnExplorerStage = findViewById(R.id.profile_stages);
        btnEtatDemande = findViewById(R.id.profile_etat);
        btnVoirCV = findViewById(R.id.profile_cv);

        // Récupère l’ID de l'étudiant passé depuis la connexion
        etudiantId = getIntent().getIntExtra("etudiant_id", -1);

        if (etudiantId != -1) {
            chargerProfilDepuisAPI(etudiantId);
        } else {
            Toast.makeText(this, "Erreur : ID étudiant manquant", Toast.LENGTH_SHORT).show();
        }

        btnMiseJour.setOnClickListener(v -> {
            Intent updateIntent = new Intent(ProfilE.this, UpdateProfile.class);
            updateIntent.putExtra("etudiant_id", etudiantId);
            updateIntent.putExtra("etudiant_nom", tvNom.getText().toString());
            updateIntent.putExtra("etudiant_prenom", tvPrenom.getText().toString());
            updateIntent.putExtra("etudiant_email", tvCourriel.getText().toString());
            updateIntent.putExtra("etudiant_url", cvUrl);
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

    private void chargerProfilDepuisAPI(int id) {
        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<List<Etudiant>> call = api.profilEtudiant(id);

        call.enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Etudiant etudiant = response.body().get(0); // car API retourne un tableau
                    tvNom.setText(etudiant.getNom());
                    tvPrenom.setText(etudiant.getPrenom());
                    tvCourriel.setText(etudiant.getEmail());
                    cvUrl = etudiant.getUrl();
                } else {
                    Toast.makeText(ProfilE.this, "Échec de récupération du profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                Log.e(TAG, "Erreur API profil", t);
                Toast.makeText(ProfilE.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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