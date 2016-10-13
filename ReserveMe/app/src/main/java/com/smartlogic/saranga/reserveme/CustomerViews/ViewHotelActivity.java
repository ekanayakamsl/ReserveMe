package com.smartlogic.saranga.reserveme.CustomerViews;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlogic.saranga.reserveme.CommonViews.LoadingActivity;
import com.smartlogic.saranga.reserveme.CommonViews.RoomListAdapter;
import com.smartlogic.saranga.reserveme.Controllers.BookingController;
import com.smartlogic.saranga.reserveme.Controllers.RoomController;
import com.smartlogic.saranga.reserveme.Models.Customer;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.Models.Room;
import com.smartlogic.saranga.reserveme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ViewHotelActivity extends AppCompatActivity {


    TextView startDateTextView;
    TextView endDateTextView;
    ListView roomListView;
    TextView nameTextView;
    TextView phoneTextView;
    TextView addressTextView;
    TextView emailTextView;
    public static TextView chargeEditText;
    public static TextView countEditText;
    public static int dateDierrent;
    public int startDate;
    public int endtDate;

    public static LinkedHashMap<String,String> bookedRooms = new LinkedHashMap<>();

    private Hotel hotel;
    private Customer customer;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener staetDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            startDateTextView.setText(sdf.format(myCalendar.getTime()));
        }

    };
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            endDateTextView.setText(sdf.format(myCalendar.getTime()));
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        startDateTextView = (TextView) findViewById(R.id.startDateTextView);
        endDateTextView = (TextView) findViewById(R.id.endDateTextView);
        roomListView = (ListView) findViewById(R.id.roomListView);
        chargeEditText = (TextView) findViewById(R.id.chargeEditText);
        countEditText = (TextView) findViewById(R.id.countEditText);

        if (getIntent().getExtras() != null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
                objectHashMap = (HashMap<String, Object>) bn.getSerializable("details");
                this.customer = (Customer) objectHashMap.get("customer");
                this.hotel = (Hotel) objectHashMap.get("hotel");
                nameTextView.setText(hotel.getName());
                if(hotel.getPhoneNumber()!="") {
                    phoneTextView.setText(hotel.getPhoneNumber());
                }
                emailTextView.setText(hotel.getEmail());
                addressTextView.setText(hotel.getLocationAddress());
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }

        RoomController roomController = new RoomController(this);
        ArrayList<Room> rooms = roomController.getRoomDetails(Integer.toString(hotel.getId()));
        if (rooms != null) {
            int length = rooms.size();
            String[] roomNumbers = new String[length];
            String[] capacities = new String[length];
            String[] charges = new String[length];
            String[] availabilities = new String[length];
            int ptr = 0;
            for (Room room : rooms) {
                roomNumbers[ptr] = Integer.toString(room.getRoom_id());
                capacities[ptr] = Integer.toString(room.getCapacity());
                charges[ptr] = Double.toString(room.getCharge());
                availabilities[ptr] = "";
                ptr ++;
            }
            roomListView.setAdapter(new RoomListAdapter(availabilities ,capacities,charges,roomNumbers,ViewHotelActivity.this));
        } else {
            roomListView.setAdapter(null);
        }
    }

    public void onStartDate(View view) {
        new DatePickerDialog(ViewHotelActivity.this, staetDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onEndDate(View view) {
        new DatePickerDialog(ViewHotelActivity.this, endDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void onReserve(View view){

        String hotel_id = Integer.toString(this.hotel.getId());
        String customer_id = Integer.toString(this.customer.getId());
        String start_date = this.startDateTextView.getText().toString();
        String end_date = this.endDateTextView.getText().toString();
        String count = this.countEditText.getText().toString();
        String charge = this.chargeEditText.getText().toString();
        String rooms = "";
        for (Map.Entry<String, String> entry : bookedRooms.entrySet()) {
            rooms = rooms + entry.getValue()+",";
        }
        if(rooms!=""){
            BookingController bookingController = new BookingController(this);

            boolean added = bookingController.makeBooking(hotel_id,customer_id,start_date,end_date,count,charge,rooms);
        }
    }

    public void onSignOut(View view){
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }
}
