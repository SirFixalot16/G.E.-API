package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Lau {
    private String prenom;
    private String nom;

    public Lau (String p, String n) {
        this.prenom = p;
        this.nom = n;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String printLau(){
        return (this.prenom + " " + this.nom);
    }

    public void set(){
        this.prenom = "Joe";
    }

    public String fetchLau() {
        try {

            // Using master api uri
            URL url = new URL(
                    "https://masterdataapi.nobelprize.org/2.0/laureates?offset=0&limit=25&birthCountry=France"
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                // Write data from uri to String
                StringBuilder infoStr = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    infoStr.append(scanner.nextLine());
                }

                scanner.close();

                // Parse to JSON Object
                JSONParser parse = new JSONParser();
                JSONObject data = (JSONObject) parse.parse(String.valueOf(infoStr));

                // Get JSON Array of Laureates
                JSONArray laureates = (JSONArray) data.get("laureates");

                // Get the first Laureate
                JSONObject person = (JSONObject) laureates.get(0);

                // Get name
                JSONObject givenName = (JSONObject) person.get("givenName");
                JSONObject familyName = (JSONObject) person.get("familyName");

                // Convert to String
                String prenom = (String) givenName.get("en");
                String nom = (String) familyName.get("en");

                this.prenom = prenom;
                this.nom = nom;
                return "set";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "not set";
        }
    }
}
