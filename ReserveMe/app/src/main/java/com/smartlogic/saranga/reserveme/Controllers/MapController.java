package com.smartlogic.saranga.reserveme.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.Models.Hotel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sranga on 6/15/2016.
 */
public class MapController {

    Context context;

    public MapController(Context context) {
        this.context = context;
    }

    public LinkedList<Hotel> getNearestHotels(String latitude, String longitude) {

        System.out.println(longitude + "=============================" + latitude);
        LinkedList<Hotel> hotels = new LinkedList<>();
        try {
            String result = new BackgroundTask().execute(latitude, longitude).get();
            System.out.println(result+"=============================" );
            String massage = "";

            if (result.equals("Error in connection")) {
                massage = "Connection Error";
            } else if (result.equals("Empty")) {
                massage = "There are no hotels near by this location";
            } else {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Hotel hotel = new Hotel();
                    hotel.setId(jsonObject.optInt("id"));
                    hotel.setName(jsonObject.optString("name"));
                    hotel.setLocationName(jsonObject.optString("loc_name"));
                    hotel.setLocationAddress(jsonObject.optString("loc_address"));
                    hotel.setPhoneNumber("phone_number ");
                    hotel.setLat(jsonObject.optDouble("lat"));
                    hotel.setLan(jsonObject.optDouble("lan"));
                    hotel.setEmail(jsonObject.optString("email"));
                    hotels.add(hotel);
                }
            }
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/read_hotels.php";
        }

        @Override
        protected String doInBackground(String... args) {

            try {

                String lat = args[0];
                String lng = args[1];

                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&" +
                        URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8");

                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String response = bufferedReader.readLine();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Error in connection";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}


