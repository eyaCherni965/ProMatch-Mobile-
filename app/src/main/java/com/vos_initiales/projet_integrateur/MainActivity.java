package com.vos_initiales.projet_integrateur;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bouton Étudiant
        Button buttonEtudiant = findViewById(R.id.buttonEtudiantImage);
        Button buttonEmp = findViewById(R.id.buttonEmploye);

        buttonEtudiant.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChoixEtudiant.class));
        });

        buttonEmp.setOnClickListener(v -> {
            // Afficher une "permission simulée"
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission requise")
                    .setMessage("Voulez-vous ouvrir le navigateur pour visiter le site ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        // Rediriger vers un lien (ex : ton site web)
                        String url = "https://www.google.com"; // <-- change le lien ici
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    })
                    .setNegativeButton("Non", null)
                    .show();
        });
    }
}
