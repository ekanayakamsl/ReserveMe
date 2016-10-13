package com.smartlogic.saranga.reserveme.HotelViews;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlogic.saranga.reserveme.CommonViews.BookingLislAdapter;
import com.smartlogic.saranga.reserveme.CommonViews.LoadingActivity;
import com.smartlogic.saranga.reserveme.Controllers.BookingController;
import com.smartlogic.saranga.reserveme.Models.Booking;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;


public class HotelMainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView addressTextView;
    private TextView emailTextView;
    private TextView dateTextView;
    ListView bookingsListView;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        bookingsListView = (ListView) findViewById(R.id.bookingsListView);

        if (getIntent().getExtras() != null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
                objectHashMap = (HashMap<String, Object>) bn.getSerializable("details");
                this.hotel = (Hotel) objectHashMap.get("hotel");
                nameTextView.setText(hotel.getName());
                phoneTextView.setText(hotel.getPhoneNumber());
                addressTextView.setText(hotel.getName());
                emailTextView.setText(hotel.getEmail());

            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }
        dateTextView.setText("2016/06/16");
        setBookingList(dateTextView.getText().toString());
    }

    public void onDate(View view) {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateTextView.setText(sdf.format(myCalendar.getTime()));
                setBookingList(dateTextView.getText().toString());
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(HotelMainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void setBookingList(String date) {
        String hotelId = Integer.toString(this.hotel.getId());
        BookingController bookingController = new BookingController(HotelMainActivity.this);
        LinkedList<Booking> bookings = bookingController.readAllHotelBookings(hotelId, date);
        if (bookings != null) {
            int length = bookings.size();
            ;
            String startDates[] = new String[length];
            String[] endDates = new String[length];
            String[] names = new String[length];
            String[] phoneNumbers = new String[length];
            String[] counts = new String[length];
            String[] charges = new String[length];
            String[] roomNumbers = new String[length];

            int tempId = -1;
            int ptr = 0;
            for (int i = 0; i < length; i++) {
                Booking booking = bookings.get(i);
                if (booking.getBooking_id() != tempId) {
                    startDates[ptr] = booking.getStart_date();
                    endDates[ptr] = booking.getEnd_date();
                    names[ptr] = booking.getCustomer_name();
                    phoneNumbers[ptr] = booking.getCustomer_phone_number();
                    roomNumbers[ptr] = Integer.toString(booking.getRoom_id());
                    charges[ptr] = Double.toString(booking.getCharge());
                    counts[ptr] = "5";
                    ptr++;
                    tempId = booking.getBooking_id();
                } else {
                    roomNumbers[ptr - 1] = roomNumbers[ptr - 1] + " " + booking.getRoom_id();
                }
            }
            HotelMainActivity.this.bookingsListView.setAdapter(new BookingLislAdapter(startDates, endDates, names, phoneNumbers, counts, charges, roomNumbers, HotelMainActivity.this));
        } else {
            this.bookingsListView.setAdapter(null);
        }
    }


    public void onAViewRoom(View view) {

        Hotel sendHotel = this.hotel;
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("hotel", hotel);
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", linkedHashMap);
        Intent intent = new Intent(this,ViewRoomActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onReserve(View view) {
    }


    public void onName(View view) {

    }

    public void onPhoneNUmber(View view) {

    }

    public void onAddress(View view) {
    }

    public void onEmail(View view) {
    }

    public void onSignOut(View view){
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }


}
