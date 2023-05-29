package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Lau FirstLau = new Lau("","");
    static List<String> arrl = new ArrayList<String>();

    public static void main(String[] args) {
        try {

            // Using master api uri
            URL url = new URL(
                    "https://masterdataapi.nobelprize.org/2.0/nobelPrizes?sort=asc&offset=0&limit=25"
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
                //System.out.println("\n"); // For testing
                //System.out.println(data);


                // Get JSON Array of Laureates
                JSONArray NB = (JSONArray) data.get("nobelPrizes");


                // Get the first Laureate
                for(int x = 0; x <25; x++){
                    JSONObject laureates = (JSONObject) NB.get(x);
                    //System.out.println("\n");
                    //System.out.println(laureates);
                    JSONArray person = (JSONArray) laureates.get("laureates");
                    //System.out.println(person);
                    JSONObject _id = (JSONObject) person.get(0);
                    //System.out.println(_id);
                    String id = (String) _id.get("id");
                    //System.out.println(id);
                    arrl.add(id);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] arr = new String[arrl.size()];
        arrl.toArray(arr);
        System.out.println(arr[1]);
        try {

            // Using master api uri
            URL url = new URL(
                    "https://masterdataapi.nobelprize.org/2.0/laureates?sort=desc&offset=0&limit=25&birthCountry=France"
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
                //System.out.println("\n"); // For testing
                System.out.println(data);

                // Get JSON Array of Laureates
                JSONArray laureates = (JSONArray) data.get("laureates");
                System.out.println(laureates);
                JSONObject person = (JSONObject) laureates.get(0);


                // Get name
                JSONObject givenName = (JSONObject) person.get("givenName");
                JSONObject familyName = (JSONObject) person.get("familyName");

                // Convert to String
                String prenom = (String) givenName.get("en");
                String nom = (String) familyName.get("en");

                FirstLau = new Lau(prenom, nom);
                String output = FirstLau.printLau();
                System.out.println(output);
                /*
                // Write onto seperate JSON file to check syntax
                try {
                    FileWriter file = new FileWriter("./output.json");
                    file.write(data.toJSONString());
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
             */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}