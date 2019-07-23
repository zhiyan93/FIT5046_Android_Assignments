package com.example.navdrawertest3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchGoogleAPI
{
    private static final String API_KEY = "AIzaSyBuy9D1hgE6ghUJs91R2v5bPPXsrtt3vog";
    private static final String SEARCH_ID_cx = "018346012164761195108:uybhro2th9g";
    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }

        return textResult;
    }

    public static String imageSearch(String keyword, String[] params, String[] values)
    {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String imageResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter+ "&searchType=image&alt=json");
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                imageResult += scanner.nextLine();
            }
            System.out.println(imageResult);

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        System.out.println(imageResult);
        return imageResult;
    }

    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        System.out.println(snippet);
        return snippet;
    }
    public static String getImage(String result) {
        String imgLink = "";

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray!=null && jsonArray.length()>0){
                imgLink = jsonArray.getJSONObject(0).getString("link");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imgLink;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
