package com.example.navdrawertest3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class EdamamAPI {
    private static final String BASE_URL =
            "https://api.edamam.com/api/food-database/parser?";
    private static final String app_key = "ec2a4dc868f7ce0e512ddadc863538cf";
    private static final String app_id = "bc28e452";

    public static String search(String keyWord)
    {
        keyWord = keyWord.replace(" ","%20");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try{
            url = new URL (BASE_URL+"ingr="+keyWord+"&app_id="+app_id+"&app_key="+app_key);
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
        //https://api.edamam.com/api/food-database/parser?ingr=apple&app_id={id}&app_key={key}
        return textResult;
    }

}

