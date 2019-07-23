package com.example.navdrawertest3;

import android.util.Log;

import com.example.navdrawertest3.RestEntity.Consumption;
import com.example.navdrawertest3.RestEntity.Credential;
import com.example.navdrawertest3.RestEntity.Food;
import com.example.navdrawertest3.RestEntity.Report;
import com.example.navdrawertest3.RestEntity.Usr;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class A1DBAPI {
    static final String BASE_URL="http://192.168.56.1:21031/5046_ass1/webresources/";

    public static String findEmail(String email){

        String result = buildGetConnect("ass1_pkg.usr/findByEmail/",true,email);
        return result;
    }
    public static String findloginName(String name){

        String result = buildGetConnect("ass1_pkg.credential/",true,name);
        return result;
    }
    public static String getCatFood(String category)
    {
        String result = buildGetConnect("ass1_pkg.food/findByCategory/",true,category);
        return result;
    }
    public static String getNewUsrId()
    {
        String result = buildGetConnect("ass1_pkg.usr/count",false,"");
        return result;
    }
    public static int getNewFoodId()
    {
        String result = buildGetConnect("ass1_pkg.food/count",false,"");
        int inc = Integer.valueOf(result) +1;
        return inc;
    }
    public static int getUIdByName(String userName)
    {
        int id = 0;
        JSONObject user = new JSONObject();
        String out = findloginName(userName);
        try {
            user = new JSONObject(out);
            id = user.getJSONObject("userId").getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }
    public static String getCalthatDay(String uId,String data)
    {
        String result = "";
        String params = ""+data+"/"+uId;
        result =  buildGetConnect("ass1_pkg.consumption/caloryConsumeThatDay/",true,params);
        return result;
       // ass1_pkg.consumption/caloryConsumeThatDay/{consumDate}/{userId}
    }
    public static int getFoodIdByName(String name)
    {
        int id = 0;
       String result =  buildGetConnect("ass1_pkg.food/findByFoodName/",true,name);
       if(result.equals("[]")) return id;
        try {
            JSONArray arr = new JSONArray(result);
          id=  arr.getJSONObject(0).getInt("foodId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }
    public static int getNewConsomId()
    {
        int id = 0;
        String result =  buildGetConnect("ass1_pkg.consumption/count",false,"");
        id = Integer.valueOf(result) +1;
        return id;
    }
    public static double getDailyBurn(String uId)
    {
        double result = 0;
        String out =  buildGetConnect("ass1_pkg.usr/dailyCaloryBurn/",true,uId);
        try {
            JSONObject obj = new JSONObject(out);
           result =  obj.getDouble("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static double getStepBurn(String uId)
    {
        double result = 0;
        String out =  buildGetConnect("ass1_pkg.usr/calorieBurnedPerStep/",true,uId);
        result = Double.valueOf(out);
        return result;
    }
    public static double[] getReport(String uId,String date)
    {
        double[] result = {0,0,0};
        String out = buildGetConnect("ass1_pkg.report/findByUserId/",true,uId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            JSONArray jsonArray = new JSONArray(out);
            for(int i=0;i<jsonArray.length();i++)
            {
              String d=  jsonArray.getJSONObject(i).getString("reportDate").substring(0,10);
              if (d.equals(date))
              {
                 result[0]=  jsonArray.getJSONObject(i).getDouble("calBurned");
                  result[1] = jsonArray.getJSONObject(i).getDouble("calConsum");
                 result[2]= jsonArray.getJSONObject(i).getDouble("calGoal");
                 break;
              }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONArray getPeriodReport(String uId,String startdate,String endDate)
    {
        JSONArray result= new JSONArray();
       String strResult = "";
        String out= buildGetConnect("ass1_pkg.report/findByUserId/",true,uId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date sdate = sdf.parse(startdate);
            Date edate = sdf.parse(endDate);
            JSONArray jsonArray = new JSONArray(out);
            for(int i=0;i<jsonArray.length();i++)
            {
                String d = jsonArray.getJSONObject(i).getString("reportDate").substring(0,10);
                Date rdate = sdf.parse(d);
                if(!sdate.after(rdate)&& !edate.before(rdate))
                {
                   result.put(jsonArray.getJSONObject(i)) ;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getAllFoodCat()
    {
        ArrayList<String> result = new ArrayList<>();
       String out= buildGetConnect("ass1_pkg.food",true,"");
        try {
            JSONArray array = new JSONArray(out);
            for(int i=0;i<array.length();i++) {
               String category= array.getJSONObject(i).getString("category");
               if(!result.contains(category))
               {
                   result.add(category);
               }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Integer getNewReportId()
    {
        int id=0;
        String out= buildGetConnect("ass1_pkg.report/count",false,"");
        id=Integer.valueOf(out)+1;
        return id;
    }


    public static void postUsr(Usr usr)
    {
        Gson gson =new Gson();
        String strUsr=gson.toJson(usr);
        buildPostConnect("ass1_pkg.usr",strUsr);
    }
    public static void postCredential(Credential credential)
    {
        Gson gson =new Gson();
        String strCredential=gson.toJson(credential);
        buildPostConnect("ass1_pkg.credential",strCredential);
    }
    public static void postFood(Food food)
    {
        Gson gson = new Gson();
        String strFood = gson.toJson(food);
        buildPostConnect("ass1_pkg.food",strFood);
    }
    public static void postConsumption(Consumption consumption)
    {
        Gson gson = new Gson();
        String strConsum = gson.toJson(consumption);
        buildPostConnect("ass1_pkg.consumption",strConsum);
    }
    public static void postReport(Report report)
    {
        Gson gson = new Gson();
        String strReport = gson.toJson(report);
        buildPostConnect("ass1_pkg.report",strReport);
    }


    private static String buildGetConnect(String methodPath,boolean json,String param)
    {
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath+param);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", json ? "application/json" : "text/plain");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            System.out.println("rest return:"+textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    private static void buildPostConnect(String methodPath,String content)
    {
        URL url = null;
        HttpURLConnection conn = null;
    try{
        url = new URL(BASE_URL + methodPath);
//open the connection
        conn = (HttpURLConnection) url.openConnection();
//set the timeout
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
//set the connection method to POST
        conn.setRequestMethod("POST");
//set the output to true
        conn.setDoOutput(true);
//set length of the data you want to send
        conn.setFixedLengthStreamingMode(content.getBytes().length);
//add HTTP headers
        conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
        PrintWriter out= new PrintWriter(conn.getOutputStream());
        out.print(content);
        System.out.println("post "+content);
        out.close();
        Log.i("error",new Integer(conn.getResponseCode()).toString());
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        conn.disconnect();
    }
    }

   /* public static String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }*/
    /*public static String findFoodCategory( String cat) {
        final String methodPath = "ass1_pkg.usr/findByEmail/"+cat;
//initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            System.out.println("count:"+textResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }*/
}
