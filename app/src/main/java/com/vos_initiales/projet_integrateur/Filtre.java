package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Filtre extends AppCompatActivity {

    private Spinner spinnerDomaine, spinnerSalaire, spinnerDuree;
    private Button btnAppliquer, btnRetour;

    private String domaineChoisi, salaireChoisi, dureeChoisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        // Récupération des vues
        spinnerDomaine = findViewById(R.id.spinner);
        spinnerSalaire = findViewById(R.id.spinner2);
        spinnerDuree = findViewById(R.id.spinner3);
        btnAppliquer = findViewById(R.id.btn_apl);
        btnRetour = findViewById(R.id.button_retour35);

        // Remplir les Spinners avec les arrays
        ArrayAdapter<CharSequence> adapterDomaine = ArrayAdapter.createFromResource(this,
                R.array.domaine_options, android.R.layout.simple_spinner_item);
        adapterDomaine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDomaine.setAdapter(adapterDomaine);

        ArrayAdapter<CharSequence> adapterSalaire = ArrayAdapter.createFromResource(this,
                R.array.salaire_options, android.R.layout.simple_spinner_item);
        adapterSalaire.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSalaire.setAdapter(adapterSalaire);

        ArrayAdapter<CharSequence> adapterDuree = ArrayAdapter.createFromResource(this,
                R.array.duree_options, android.R.layout.simple_spinner_item);
        adapterDuree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuree.setAdapter(adapterDuree);

        // Sauvegarde des choix utilisateur
        spinnerDomaine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                domaineChoisi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerSalaire.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salaireChoisi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerDuree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dureeChoisie = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Bouton Retour vers UpdateProfile
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(Filtre.this, ProfilE.class);
            startActivity(intent);
            finish();
        });





        // Bouton Appliquer vers SwipingActivity 
        btnAppliquer.setOnClickListener(v -> {
            Intent intent = new Intent(Filtre.this, SwipingActivity.class);

            boolean auMoinsUnFiltre = false;

            if (!"Tous les domaines".equals(domaineChoisi)) {
                intent.putExtra("domaine", domaineChoisi);
                auMoinsUnFiltre = true;
            }

            if (!"Tous les salaires".equals(salaireChoisi)) {
                intent.putExtra("salaire", salaireChoisi);
                auMoinsUnFiltre = true;
            }

            if (!"Toutes durées".equals(dureeChoisie)) {
                intent.putExtra("duree", dureeChoisie);
                auMoinsUnFiltre = true;
            }

            if (!auMoinsUnFiltre) {
                Toast.makeText(Filtre.this, "Aucun filtre appliqué. Tous les stages seront affichés.", Toast.LENGTH_SHORT).show();
            }

            startActivity(intent);
        });


    }
}
