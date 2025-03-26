package com.vos_initiales.projet_integrateur;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.Statement;



public class DatabaseTest {

    private static final String TAG = "DB_CONNECTION";

    public static void main(String[] args) {

        // URL de connexion à la base de données

        String url = "jdbc:sqlserver://mysqlserver0411.database.windows.net:1433;database=mySampleDatabase";

        String username = "azureuser";

        String password = "Promatch123.";



        try {

            // Établir la connexion

            Connection connection = DriverManager.getConnection(url, username, password);



            // Créer une déclaration

            Statement statement = connection.createStatement();



            // Exécuter la requête SQL

            String query = "SELECT nom, prenom FROM ETUDIANT";

            ResultSet resultSet = statement.executeQuery(query);



            // Lire les résultats

            while (resultSet.next()) {

                String nom = resultSet.getString("nom");

                String prenom = resultSet.getString("prenom");

                System.out.println("Nom: " + nom + ", Prénom: " + prenom);

            }



            // Fermer les ressources

            resultSet.close();

            statement.close();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}