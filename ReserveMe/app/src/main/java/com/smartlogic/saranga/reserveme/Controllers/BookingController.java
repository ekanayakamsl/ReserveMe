package com.smartlogic.saranga.reserveme.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.Models.Booking;
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
 * Created by sranga on 6/14/2016.
 */
public class BookingController {
    Context context;

    public BookingController(Context context) {
        this.context = context;
    }

    public LinkedList<Booking> readAllHotelBookings(String hotelId, String date) {
        try {
            String result = new HotelBookingsBackgroundTask().execute().get();
            LinkedList<Booking> bookings = new LinkedList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Booking booking = new Booking();
                    booking.setBooking_id(jsonObject.optInt("booking_id"));
                    booking.setRoom_id(jsonObject.optInt("room_id"));
                    booking.setHotel_id(jsonObject.optInt("hotel_id"));
                    booking.setCustomer_id(jsonObject.optInt("customer_id"));
                    booking.setStart_date(jsonObject.optString("start_date"));
                    booking.setEnd_date(jsonObject.optString("end_date"));
                    booking.setCharge(jsonObject.optDouble("charge"));
                    booking.setCustomer_name(jsonObject.optString("name"));
                    booking.setCustomer_phone_number(jsonObject.optString("phone_number"));
                    booking.setCustomer_email(jsonObject.optString("email"));
                    bookings.add(booking);
                }
                return bookings;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinkedList<Booking> readCustomerAllBookings(String customerId, String date) {
        try {

            String result = new CustomreBookingsBackgroundTask().execute(customerId, date).get();
            LinkedList<Booking> bookings = new LinkedList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Booking booking = new Booking();

                    Hotel hotel = new Hotel();
                    hotel.setId(jsonObject.optInt("hotel_id"));
                    hotel.setName(jsonObject.optString("name"));
                    hotel.setPhoneNumber(jsonObject.optString("phone_number"));
                    hotel.setEmail(jsonObject.optString("email"));
                    hotel.setLat(jsonObject.optDouble("lat"));
                    hotel.setLan(jsonObject.optDouble("lan"));
                    hotel.setLocationName(jsonObject.optString("loc_name"));
                    hotel.setLocationAddress(jsonObject.optString("loc_address"));

                    booking.setHotel(hotel);
                    booking.setBooking_id(jsonObject.optInt("booking_id"));
                    booking.setRoom_id(jsonObject.optInt("room_id"));
                    booking.setStart_date(jsonObject.optString("start_date"));
                    booking.setEnd_date(jsonObject.optString("end_date"));
                    booking.setCharge(jsonObject.optDouble("charge"));

                    bookings.add(booking);
                }
                return bookings;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean makeBooking(String hotel_id, String customer_id, String start_date, String end_date, String count, String charge, String rooms) {

        boolean added = false;
        BookingBackgroundTask signUpBackgroundTask = new BookingBackgroundTask();
        try {
            String result = signUpBackgroundTask.execute(hotel_id,customer_id,start_date,end_date,count,charge,rooms).get();
            String massage = " ";
            if(result.equals("Successfully added")){
                massage = "Reservation is successful";
                added = true;
            }else if(result.equals("Error..")){
                massage = "Error..";
            }else if(result.equals("Error")){
                massage = "Error";
            }else if(result.equals("Connection Error")){
                massage = "Connection Error";
            }
            Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return added;
    }


    class BookingBackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/addBooking.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String hotel_id = args[0];
            String customer_id = args[1];
            String start_date = args[2];
            String end_date = args[3];
            String count = args[4];
            String charge = args[5];
            String rooms = args[6];

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("hotel_id", "UTF-8") + "=" + URLEncoder.encode(hotel_id, "UTF-8") + "&" +
                        URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(customer_id, "UTF-8") + "&" +
                        URLEncoder.encode("start_date", "UTF-8") + "=" + URLEncoder.encode(start_date, "UTF-8")+ "&" +
                        URLEncoder.encode("end_date", "UTF-8") + "=" + URLEncoder.encode(end_date, "UTF-8")+"&" +
                        URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode(count, "UTF-8")+"&" +
                        URLEncoder.encode("charge", "UTF-8") + "=" + URLEncoder.encode(charge, "UTF-8")+"&" +
                        URLEncoder.encode("rooms", "UTF-8") + "=" + URLEncoder.encode(rooms, "UTF-8");

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
            }return "Error..";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    class HotelBookingsBackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;
        private final ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/read_all_hotel_booking.php";
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
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
            return "Error";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            String massage = " ";
            if (result.equals("Empty")) {
                massage = "There are no bookings";
            } else if (result.equals("Error in connection")) {
                massage = "Error in connection";
            } else if (result.equals("Error")) {
                massage = "Cannot connect to the network";
            } else {
                massage = "Success...........";
            }
            Toast.makeText(context.getApplicationContext(), massage, Toast.LENGTH_LONG).show();
            this.dialog.dismiss();
        }
    }

    class CustomreBookingsBackgroundTask extends AsyncTask<String, Void, String> {

        String addCustomerUrl;
        private final ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            addCustomerUrl = "http://ekanayakamsl.net23.net/read_all_customer_booking.php";
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            String customerId = args[0];
            String date = args[1];

            try {
                URL url = new URL(addCustomerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String dataString = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(customerId, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");

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
            return "Error";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            String massage = " ";
            if (result.equals("Empty")) {
                massage = "There are no bookings";
            } else if (result.equals("Error in connection")) {
                massage = "Error in connection";
            } else if (result.equals("Error")) {
                massage = "Cannot connect to the network";
            } else {
                massage = "Success";
            }
            Toast.makeText(context.getApplicationContext(), massage, Toast.LENGTH_LONG).show();
            this.dialog.dismiss();
        }
    }


}
