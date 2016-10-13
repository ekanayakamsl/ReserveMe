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

import com.smartlogic.saranga.reserveme.CommonViews.BookingLislAdapter;
import com.smartlogic.saranga.reserveme.CommonViews.LoadingActivity;
import com.smartlogic.saranga.reserveme.Controllers.BookingController;
import com.smartlogic.saranga.reserveme.Models.Booking;
import com.smartlogic.saranga.reserveme.Models.Customer;
import com.smartlogic.saranga.reserveme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

public class CustomerMainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView dateTextView;
    private ListView bookingListView;
    public Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        bookingListView = (ListView) findViewById(R.id.bookingListView);

        if (getIntent().getExtras() != null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
                objectHashMap = (HashMap<String, Object>) bn.getSerializable("details");
                Customer customer = (Customer) objectHashMap.get("customer");
                this.customer = customer;
                nameTextView.setText(customer.getName());
                phoneTextView.setText(customer.getPhoneNumber());
                emailTextView.setText(customer.getEmail());
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }

        dateTextView.setText("2016/06/16");
        setBookingList(dateTextView.getText().toString());
    }

    public void onReserve(View view) {

        LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("customer", this.customer);
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", linkedHashMap);
        Intent intent = new Intent(this, HotelMapActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
        DatePickerDialog dpd = new DatePickerDialog(CustomerMainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void setBookingList(String date) {
        String customerId = Integer.toString(this.customer.getId());
        BookingController bookingController = new BookingController(CustomerMainActivity.this);
        LinkedList<Booking> bookings = bookingController.readCustomerAllBookings(customerId, date);
        if (bookings != null) {
            int length = bookings.size();
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
                    names[ptr] = booking.getHotel().getName();
                    phoneNumbers[ptr] = booking.getHotel().getPhoneNumber();
                    roomNumbers[ptr] = Integer.toString(booking.getRoom_id());
                    charges[ptr] = Double.toString(booking.getCharge());
                    counts[ptr] = "5";
                    ptr++;
                    tempId = booking.getBooking_id();
                } else {
                    roomNumbers[ptr - 1] = roomNumbers[ptr - 1] + " " + booking.getRoom_id();
                }
            }
            CustomerMainActivity.this.bookingListView.setAdapter(new BookingLislAdapter(startDates, endDates, names, phoneNumbers, counts, charges, roomNumbers, CustomerMainActivity.this));
        } else {
            this.bookingListView.setAdapter(null);
        }
    }

    public void onSignOut(View view){
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }


}
