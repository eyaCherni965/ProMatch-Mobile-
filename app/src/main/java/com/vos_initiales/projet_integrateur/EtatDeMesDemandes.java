package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class EtatDeMesDemandes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DemandeAdapter adapter;
    private List<Demande> listeDemandes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_de_mes_demandes);

        recyclerView = findViewById(R.id.recyclerViewDemandes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listeDemandes = new ArrayList<>();
        adapter = new DemandeAdapter(listeDemandes);
        recyclerView.setAdapter(adapter);

        // Récupère l'id de l'étudiant connecté
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int idEtudiant = sharedPreferences.getInt("etudiant_id", -1);

        if (idEtudiant != -1) {
            chargerDemandes(idEtudiant);
        } else {
            Toast.makeText(this, "ID étudiant introuvable", Toast.LENGTH_SHORT).show();
        }
        Button btnRetour = findViewById(R.id.btnRetourProfil);
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(EtatDeMesDemandes.this, ProfilE.class);
            startActivity(intent);
            finish();
        });

    }

    private void chargerDemandes(int idEtudiant) {
        EtudiantAPI api = RetrofitClient.getClient().create(EtudiantAPI.class);
        Call<List<Demande>> call = api.getDemandes(idEtudiant);

        call.enqueue(new Callback<List<Demande>>() {
            @Override
            public void onResponse(Call<List<Demande>> call, Response<List<Demande>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listeDemandes.clear();
                    listeDemandes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(EtatDeMesDemandes.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Demande>> call, Throwable t) {
                Toast.makeText(EtatDeMesDemandes.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
