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
import java.util.concurrent.ExecutionException;

/**
 * Created by sranga on 6/15/2016.
 */
public class HotelController {

    static Context context;

    public HotelController(Context context) {
        this.context = context;
    }

    public Hotel signIn(String phoneNumber, String password, String accountType) {
        SignInBackgroundTask signInBackgroundTask = new SignInBackgroundTask();
        try {
            String result = signInBackgroundTask.execute(phoneNumber, password, accountType).get();
            String massage = "";
            if (result.equals("Error in connection")) {
                massage = "Connection Error";
            } else if (result.equals("Incorrect")) {
                massage = "Phone number or pass word you have entered is incorrect";
            } else {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Hotel hotel = new Hotel();
                hotel.setId(Integer.parseInt(jsonObject.optString("id").toString()));
                hotel.setName(jsonObject.optString("name").toString());
                hotel.setPhoneNumber(jsonObject.optString("phone_number").toString());
                hotel.setPassWord(jsonObject.optString("password").toString());
                hotel.setEmail(jsonObject.optString("email").toString());
                hotel.setLat(Double.parseDouble(jsonObject.optString("lat").toString()));
                hotel.setLan(Double.parseDouble(jsonObject.optString("lan").toString()));
                hotel.setLocationName(jsonObject.optString("password").toString());
                hotel.setLocationAddress(jsonObject.optString("loc_address").toString());
                return hotel;
            }
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean suinUp(String name, String phoneNumber, String password, String email, String lat, String lng, String locationName) {
        boolean signUp = false;
        SignUpBackgroundTask signUpBackgroundTask = new SignUpBackgroundTask();
        try {
            String result = signUpBackgroundTask.execute(name, phoneNumber, password, email, lat , lng ,locationName).get();
            String massage = " ";
            if(result.equals("Phone number is exist")){
                massage = "This phone number already have an account";
            }else if(result.equals("Successfully added")){
                massage = "Registration is successful";
                signUp = true;
            }else if(result.equals("Error")){
                massage = "Connection Error";
            }
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return signUp;
    }

    class SignUpBackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/hotel_signup.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String name = args[0];
            String phoneNumber = args[1];
            String password = args[2];
            String email = args[3];
            String lat = args[4];
            String lng = args[5];
            String locationName = args[6];

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"+
                        URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8")+"&"+
                        URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8")+"&"+
                        URLEncoder.encode("location_name", "UTF-8") + "=" + URLEncoder.encode(locationName, "UTF-8");

                System.out.println(dataString);
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String response = bufferedReader.readLine();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Error";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
        }

    }


    class SignInBackgroundTask extends AsyncTask<String, Void, String> {
        String addCustomerUrl;

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/signin.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String phoneNumber = args[0];
            String password = args[1];
            String accountType = args[2];

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("account_type", "UTF-8") + "=" + URLEncoder.encode(accountType, "UTF-8");

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
