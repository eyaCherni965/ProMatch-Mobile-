package com.vos_initiales.projet_integrateur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bouton Étudiant
        Button buttonEtudiant = findViewById(R.id.buttonEtudiantImage);
        buttonEtudiant.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChoixEtudiant.class));
        });

        // Bouton Employé
        Button buttonEmp = findViewById(R.id.buttonEmploye);
        buttonEmp.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "La section employeur sera disponible bientôt.", Toast.LENGTH_LONG).show();
        });
    }
}
