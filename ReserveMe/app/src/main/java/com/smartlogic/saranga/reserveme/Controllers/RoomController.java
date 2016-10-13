package com.smartlogic.saranga.reserveme.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.Models.Room;

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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sranga on 6/15/2016.
 */
public class RoomController {

    Context context;

    public RoomController(Context context) {
        this.context = context;
    }

    public ArrayList<Room> getRoomDetails(String hotelId) {
        try {
            String result = new RoomsBackgroundTask().execute(hotelId).get();
            ArrayList<Room> rooms = new ArrayList<>();
            String massage = " ";
            System.out.println( hotelId+"====================="+result);
            if (result.equals("Error in connection")) {
                massage = "Error in connection";
            } else if (result.equals("Empty")) {
                massage = "There are no rooms for reserve";
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Room room = new Room();
                        room.setHotel_id(jsonObject.optInt("hotel_id"));
                        room.setRoom_id(jsonObject.optInt("room_id"));
                        room.setCapacity(jsonObject.optInt("capacity"));
                        room.setCharge(jsonObject.optDouble("charge"));
                        rooms.add(room);
                    }
                    return rooms;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addRoom(String hotel_id, String capacity, String charge) {

    }


    class RoomsBackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/read_rooms.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String hotel_id = args[0];

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("hotel_id", "UTF-8") + "=" + URLEncoder.encode(hotel_id, "UTF-8");

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
