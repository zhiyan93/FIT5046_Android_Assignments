package com.example.navdrawertest3;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
public class FoursquareAPI {
    private static final String BASE_URL = "https://api.foursquare.com/v2/venues/search?";
    private static final String client_id = "&client_id=NIZITWEPGEE12LQIABLVDIRJ1GDLLAPMNKYPKRVGEGZV04PQ";
    private static final String client_secret = "&client_secret=0VVE5RGJVUELWU0S2HITVVEX0SFHQREYJGOMCVAB0ETLQUK2";
    private static final String version = "&v=20190514";
    private static final String param = "&categoryId=4bf58dd8d48988d163941735&radius=5000";

    public static String searchParks(double lati,double longi)
    {

        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try{
            url = new URL (BASE_URL+"ll="+lati+","+longi+param+client_id+client_secret+version);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                textResult += scanner.nextLine();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("textResult "+textResult);

        return textResult;
    }
}
