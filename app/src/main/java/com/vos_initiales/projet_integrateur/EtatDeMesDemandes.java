package com.vos_initiales.projet_integrateur;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        listeDemandes.add(new Demande("Stage en génie logiciel", "accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "en attente"));
        listeDemandes.add(new Demande("Stage IA", "refusé"));
        listeDemandes.add(new Demande("Stage en génie logiciel II", "Accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "accepté"));
        listeDemandes.add(new Demande("Stage IA", "accepté"));
        listeDemandes.add(new Demande("Stage en génie logiciel III", "accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "en attente"));
        listeDemandes.add(new Demande("Stage IA", "refusé"));
        listeDemandes.add(new Demande("Stage en génie industriel", "accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "en attente"));
        listeDemandes.add(new Demande("Stage IA", "refusé"));
        listeDemandes.add(new Demande("Stage GPA", "accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "en attente"));
        listeDemandes.add(new Demande("Stage IC", "refusé"));
        listeDemandes.add(new Demande("Stage en génie logiciel", "accepté"));
        listeDemandes.add(new Demande("Développeur mobile", "en attente"));
        listeDemandes.add(new Demande("Stage IB", "refusé"));


        adapter = new DemandeAdapter(listeDemandes);
        recyclerView.setAdapter(adapter);
    }
}
