package com.example.openweatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String zipCode;
    EditText userInput;
    Button button;
    ImageView image, image1, image2, image3, image4, image5;
    TextView loc, lat, lon, quote, currentTemp, date1, date2, date3, date4, date5, weather1, weather2, weather3, weather4, weather5, high1, high2, high3, high4, high5, low1, low2, low3, low4, low5;

    String longitude, latitude, location, current, day, dates;
    Double min, max;
    Boolean error = false;

    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> weatherMain = new ArrayList<String>();
    ArrayList<Double> highTemp = new ArrayList<Double>();
    ArrayList<Double> lowTemp = new ArrayList<Double>();
    ArrayList<Integer> pictures = new ArrayList<Integer>();
    ArrayList<String> quotes = new ArrayList<String>();
    ArrayList<Integer> mainPic = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.zipCode);
        button = findViewById(R.id.search);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        image4 = findViewById(R.id.imageView4);
        image5 = findViewById(R.id.imageView5);

        loc = findViewById(R.id.location);
        lat = findViewById(R.id.latitude);
        lon = findViewById(R.id.longitude);
        quote = findViewById(R.id.quote);
        currentTemp = findViewById(R.id.currentTemp);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        weather1 = findViewById(R.id.weather1);
        weather2 = findViewById(R.id.weather2);
        weather3 = findViewById(R.id.weather3);
        weather4 = findViewById(R.id.weather4);
        weather5 = findViewById(R.id.weather5);
        high1 = findViewById(R.id.high1);
        high2 = findViewById(R.id.high2);
        high3 = findViewById(R.id.high3);
        high4 = findViewById(R.id.high4);
        high5 = findViewById(R.id.high5);
        low1 = findViewById(R.id.low1);
        low2 = findViewById(R.id.low2);
        low3 = findViewById(R.id.low3);
        low4 = findViewById(R.id.low4);
        low5 = findViewById(R.id.low5);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 zipCode = userInput.getText().toString();
                 AsyncThread task = new AsyncThread();
                 task.execute(zipCode);
             }
         });

    }//closes onCreate

    public class AsyncThread extends AsyncTask<String, Void, JSONObject> {
        @SuppressLint("ResourceType")
        @Override
        protected JSONObject doInBackground(String... strings) {
            // This is where you will download your data.
            try {
                URL url, urlWeather;
                URLConnection connection, connection2;
                InputStream input, input2;
                String info, info2;
                JSONObject object, weather;
                ArrayList<JSONObject> forecast = new ArrayList<JSONObject>();

                url = new URL("https://api.openweathermap.org/geo/1.0/zip?zip=" + zipCode + ",US&appid=c00745ba879fc182da943fba8063a56a");
                connection = url.openConnection();

                date.clear();
                weatherMain.clear();
                highTemp.clear();
                lowTemp.clear();
                pictures.clear();
                quotes.clear();
                mainPic.clear();

//zipCode tryCatch
                try {
                    input = connection.getInputStream();
                } catch (FileNotFoundException e) {
                    Log.d("API Error", e.getMessage());
                    error = true;
                    return null;
                }
                error = false;

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                info = reader.readLine();
                object = new JSONObject(info);

                Log.d("LOCATION", object.toString());
                longitude = object.get("lon").toString();
                latitude = object.get("lat").toString();
                location = object.get("name").toString();
                Log.d("longitude", longitude);
                Log.d("latitude", latitude);
                Log.d("place", location);

                urlWeather = new URL("https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=c00745ba879fc182da943fba8063a56a&units=imperial");
                connection2 = urlWeather.openConnection();
                input2 = connection2.getInputStream();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(input2));
                info2 = reader2.readLine();
                weather = new JSONObject(info2);

                Log.d("WEATHER", weather.toString());
                JSONArray weatherArr = weather.getJSONArray("list");
                for (int i = 0; i < 40; i += 8) {
                    forecast.add(weatherArr.getJSONObject(i));
                }
                Log.d("WEATHER2", forecast.toString());

                JSONObject main1 = forecast.get(0).getJSONObject("main");

                    //weather
                    for (int i = 0; i < 5; i++) {
                        day = (forecast.get(i).getJSONArray("weather")).getJSONObject(0).getString("description");
                        weatherMain.add(day);
                    }
                    Log.d("d1", String.valueOf(weatherMain));

                    //current temp
                    current = main1.get("temp").toString();
                    Log.d("current", current);

                    //low temperature
                    for (int i = 0; i < 5; i++) {
                        min = Double.valueOf((forecast.get(i).getJSONObject("main")).get("temp_min").toString());
                        lowTemp.add(min);
                    }
                    Log.d("min", String.valueOf(lowTemp));

                    //high temperature
                    for (int i = 0; i < 5; i++) {
                        max = Double.valueOf((forecast.get(i).getJSONObject("main")).get("temp_max").toString());
                        highTemp.add(max);
                    }
                    Log.d("max", String.valueOf(highTemp));

                    //date
                    for (int i = 0; i < 5; i++) {
                        dates = String.valueOf(forecast.get(i).getString("dt_txt"));
                        int num = dates.indexOf(" ");
                        String temp = dates.substring(5, num);
                        date.add(temp);
                    }
                    Log.d("dates", String.valueOf(date));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }// doInBackground

        @Override
        protected void onPostExecute(JSONObject d) {
            super.onPostExecute(d);

            if (error) {
                Toast.makeText(MainActivity.this, "Invalid Zip Code. Input Another One", Toast.LENGTH_SHORT).show();
                return;
            }
            loc.setText(location);
            lat.setText("Latitude: " + latitude);
            lon.setText("Longitude: " + longitude);

            currentTemp.setText("Current Temperature: " + current + " °F");

//setting low and high and date
            for (int i = 0; i < lowTemp.size(); i++) {
                switch (i) {
                    case 0: {
                        low1.setText("Low: " + String.valueOf(lowTemp.get(i)) + " °F");
                        high1.setText("High: " + String.valueOf(highTemp.get(i)) + " °F");
                        date1.setText(date.get(i));
                    }
                    case 1: {
                        low2.setText("Low: " + String.valueOf(lowTemp.get(i)) + " °F");
                        high2.setText("High: " + String.valueOf(highTemp.get(i)) + " °F");
                        date2.setText(date.get(i));
                    }
                    case 2: {
                        low3.setText("Low: " + String.valueOf(lowTemp.get(i)) + " °F");
                        high3.setText("High: " + String.valueOf(highTemp.get(i)) + " °F");
                        date3.setText(date.get(i));
                    }
                    case 3: {
                        low4.setText("Low: " + String.valueOf(lowTemp.get(i)) + " °F");
                        high4.setText("High: " + String.valueOf(highTemp.get(i)) + " °F");
                        date4.setText(date.get(i));
                    }
                    case 4: {
                        low5.setText("Low: " + String.valueOf(lowTemp.get(i)) + " °F");
                        high5.setText("High: " + String.valueOf(highTemp.get(i)) + " °F");
                        date5.setText(date.get(i));
                    }
                }
            }

            for (int i = 0; i < weatherMain.size(); i++) {
                if (weatherMain.get(i).equals("overcast clouds") || weatherMain.get(i).equals("broken clouds") || weatherMain.get(i).equals("few clouds") || weatherMain.get(i).equals("scattered clouds")) {
                    pictures.add(R.drawable.cloudy);
                    quotes.add("\"The night was so cloudy\n" +
                             "It's not my fault I just couldn't see\"\n- i wished on a plane");
                    mainPic.add(R.drawable.aestcloud);
                }
                else if (weatherMain.get(i).equals("light rain")) {
                    pictures.add(R.drawable.drizzle);
                    quotes.add("\"He was sunshine, I was midnight rain\"\n- midnight rain");
                    mainPic.add(R.drawable.aestlightrain);
                }
                else if (weatherMain.get(i).equals("moderate rain") || weatherMain.get(i).equals("shower rain") || weatherMain.get(i).equals("rain")) {
                    pictures.add(R.drawable.rain);
                    quotes.add("\"Show me a gray sky, a rainy cab ride\"\n-cornelia street");
                    mainPic.add(R.drawable.aestrainy);
                }
                else if (weatherMain.get(i).equals("thunderstorm")){
                    pictures.add(R.drawable.stormy);
                    quotes.add("\"with you i’d dance in a storm in my best dress fearless\"\n- fearless");
                    mainPic.add(R.drawable.aeststorm);
                }
                else if (weatherMain.get(i).equals("snow")) {
                    pictures.add(R.drawable.snowy);
                    quotes.add("\"Sidewalk chalk covered in snow\n" +
                             "Lost my gloves, you give me one\"\n- it's nice to have a friend");
                    mainPic.add(R.drawable.aestsnow);
                }
                else if (weatherMain.get(i).equals("windy")) {
                    pictures.add(R.drawable.windy);
                    quotes.add("\"Life was a willow and it bent right to your wind\"\n- willow");
                    mainPic.add(R.drawable.aestwind);
                }
                else if (weatherMain.get(i).equals("clear sky")) {
                    pictures.add(R.drawable.sunny);
                    quotes.add("\"And I hope the sun shines and it's a beautiful day\"\n- last kiss");
                    mainPic.add(R.drawable.aestsky);
                }
            }

//setting quote and picture
            quote.setText(quotes.get(0));
            image.setImageResource(mainPic.get(0));

//setting weather and pictures
            for (int i = 0; i < weatherMain.size(); i++) {
                switch (i) {
                    case 0: {
                        weather1.setText(weatherMain.get(i));
                        image1.setImageResource(pictures.get(i));
                    }
                    case 1: {
                        weather2.setText(weatherMain.get(i));
                        image2.setImageResource(pictures.get(i));
                    }
                    case 2: {
                        weather3.setText(weatherMain.get(i));
                        image3.setImageResource(pictures.get(i));
                    }
                    case 3: {
                        weather4.setText(weatherMain.get(i));
                        image4.setImageResource(pictures.get(i));
                    }
                    case 4: {
                        weather5.setText(weatherMain.get(i));
                        image5.setImageResource(pictures.get(i));
                    }
                }
            }
        }//closes onPostExecute

    }

}