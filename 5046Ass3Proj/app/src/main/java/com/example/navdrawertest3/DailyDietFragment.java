package com.example.navdrawertest3;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.navdrawertest3.RestEntity.Consumption;
import com.example.navdrawertest3.RestEntity.Food;
import com.example.navdrawertest3.RestEntity.foodId;
import com.example.navdrawertest3.RestEntity.userId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
public class DailyDietFragment extends Fragment  {
    private View vHome;
    private ListView foodList;
    private ListView catList;

    private EditText eSearch;
    private TextView vResult,vFoodInfo;
    private Button searchBtn,addBtn,consumeBtn;
    private ImageView foodImage;
    private Food newFood ;
    private ProgressBar foodprogress,newFoodProg;
    private SharedPreferences sp;
    private String userName = "";
    private String foodName = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vHome = inflater.inflate(R.layout.daily_diet, container, false);
        foodList = vHome.findViewById(R.id.foodList);
        catList = vHome.findViewById(R.id.catList);

        eSearch= vHome.findViewById(R.id.eSearch);
        vResult= vHome.findViewById(R.id.vResult);
        vFoodInfo = vHome.findViewById(R.id.vFoodInfo);
        searchBtn= vHome.findViewById(R.id.searchBtn);
        foodImage = vHome.findViewById(R.id.foodImage);
        addBtn = vHome.findViewById(R.id.addBtn);
        foodprogress = vHome.findViewById(R.id.foodprogress);
        newFoodProg = vHome.findViewById(R.id.newFoodProg);
        consumeBtn = vHome.findViewById(R.id.consumeBtn);
        foodprogress.setVisibility(View.INVISIBLE);
        newFoodProg.setVisibility(View.INVISIBLE);
     //   List<String> categories = Arrays.asList("fruit","vegetable","sauce","meat","bread","sugar","drink","Generic foods");
      //  ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_text, categories);
        sp = ((MainActivity)getActivity()).getSP();
        userName= sp .getString("userName","");
        // Set the adapter for ListView
       // catList.setAdapter(langAdapter);
        FindAllCateTask findAllCateTask = new FindAllCateTask();
        findAllCateTask.execute();
        // Set a click listener for ListView items
        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get selected item text
                String item = (String) adapterView.getItemAtPosition(i);
                GetCatFoodTask task = new GetCatFoodTask();
                task.execute(item);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                      PostFoodTask foodTask = new PostFoodTask();
                                      foodTask.execute(newFood);
                                      }
                                  });

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);
                foodName = item;
                SearchFoodTask task = new SearchFoodTask();
                task.execute(item);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAsyncTask task = new SearchAsyncTask();
                task.execute();
                SearchFoodInfoTask infoTask = new SearchFoodInfoTask();
                infoTask.execute();
            }
        });
        consumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            PostConsumptionTask task = new PostConsumptionTask();
            task.execute();
            }
        });

        return vHome;
    }

    private class GetCatFoodTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return A1DBAPI.getCatFood(params[0]);
        }

        @Override
        protected void onPostExecute(String foods)
        {
            JSONArray foodArr;
            ArrayList<String> names = new ArrayList<>();
            String name = "";
            try {
                 foodArr = new JSONArray(foods);
                 for(int i=0;i<foodArr.length();i++)
                 {
                     name = foodArr.getJSONObject(i).getString("foodName");
                     names.add(name);
                 }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_text, names);
            foodList.setAdapter(langAdapter);

        }
    }

    private class PostFoodTask extends AsyncTask<Food,Void,Void>
    {

        @Override
        protected Void doInBackground(Food... foods) {
            if(newFood==(null)) return null;
           A1DBAPI.postFood(foods[0]);
             return null;
        }
    }


    private class SearchAsyncTask extends AsyncTask<String,Void,String>{

        private String text ="";
        private String category,calAmt,fat = "";
        int id = 0;
        @Override
        protected void onPreExecute(){

           text= eSearch.getText().toString();
            newFoodProg.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params) {
            if (text.equals("")) return "";
            String rawResult = EdamamAPI.search(text);
            String outText = "";

            try {
                JSONObject food = new JSONObject(rawResult).getJSONArray("parsed").getJSONObject(0).getJSONObject("food");
                calAmt = food.getJSONObject("nutrients").getString("ENERC_KCAL");
                fat = food.getJSONObject("nutrients").getString("FAT");
                category =food.getString("category");
                id = A1DBAPI.getNewFoodId();
                newFood = new Food(id,Double.valueOf(calAmt),Double.valueOf(fat),category,text,1.0,"100g" );
                outText = food.getJSONObject("nutrients").toString() + " category:"+category;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return outText ;
        }
        @Override
        protected void onPostExecute (String result)
        {
            newFoodProg.setVisibility(View.INVISIBLE);
            vResult.setText(result);
        }
    }

    private class SearchFoodInfoTask extends AsyncTask<Void,Void,String>
    {
        private String text = "";
        @Override
        protected void onPreExecute()
        {
            text= eSearch.getText().toString();
        }
        @Override
        protected String doInBackground(Void... voids) {
         String result= SearchGoogleAPI.getSnippet(SearchGoogleAPI.search(text,new String[]{"num"},new String[]{"1"}));
         return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
           int index = result.indexOf('.');
           if (index>1)
           {vFoodInfo.setText(result.substring(0,index).replaceAll("\n",""));}
           else {
               vFoodInfo.setText(result.replaceAll("\n",""));
           }

        }
    }

    private class SearchFoodTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute()
        {
            foodprogress.setVisibility(View.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageObject = SearchGoogleAPI.imageSearch(params[0], new String[]{"num"}, new
                    String[]{"1"});
            String imageLink = SearchGoogleAPI.getImage(imageObject);
            Bitmap image = SearchGoogleAPI.getBitmapFromURL(imageLink);
            return image;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            foodprogress.setVisibility(View.INVISIBLE);
            foodImage.setImageBitmap(result);
        }
    }

    private class PostConsumptionTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
         int uid = A1DBAPI.getUIdByName(userName);
         int fId = A1DBAPI.getFoodIdByName(foodName);
         int consumId = A1DBAPI.getNewConsomId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String date= sdf.format(new Date());
            Consumption consumption = new Consumption(consumId,date+"+11:00",1,new foodId(String.valueOf(fId)),new userId(String.valueOf(uid)));
            A1DBAPI.postConsumption(consumption);
            return null;
        }
    }

    private class FindAllCateTask extends AsyncTask<Void,Void,ArrayList<String>>

    {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
             ArrayList<String> arrayList= A1DBAPI.getAllFoodCat();
             return arrayList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> result)
        {
              // List<String> categories = result;// Arrays.asList("fruit","vegetable","sauce","meat","bread","sugar","drink","Generic foods");
              ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_text, result);
            catList.setAdapter(langAdapter);
        }
    }
}
