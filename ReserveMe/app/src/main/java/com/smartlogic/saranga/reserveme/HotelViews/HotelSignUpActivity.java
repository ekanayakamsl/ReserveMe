package com.smartlogic.saranga.reserveme.HotelViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.CommonViews.LogInActivity;
import com.smartlogic.saranga.reserveme.Controllers.HotelController;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.R;
import com.smartlogic.saranga.reserveme.Validation;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class HotelSignUpActivity extends AppCompatActivity {

    Validation validation;
    private EditText nameEditText;
    private EditText phoneNumbEditText;
    private EditText emailEditText;
    private EditText passWordEditText;
    private EditText locationEditText;
    private double lat;
    private double lng;
    private String locationName;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_sign_up);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        phoneNumbEditText = (EditText) findViewById(R.id.phoneNumberEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passWordEditText = (EditText) findViewById(R.id.passwordEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);

        if(getIntent().getExtras()!=null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> getobj = new HashMap<String, Object>();
                getobj = (HashMap<String, Object>) bn.getSerializable("details");
                this.hotel = (Hotel) getobj.get("hotel");
                this.lat = hotel.getLat();
                this.lng = hotel.getLan();
                this.locationName = hotel.getLocationName();
                this.nameEditText.setText(hotel.getName());
                this.phoneNumbEditText.setText(hotel.getPhoneNumber());
                this.emailEditText.setText(hotel.getEmail());
                this.locationEditText.setText(hotel.getLocationName());
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }else{
            this.hotel = new Hotel();
        }

        validation = new Validation();
        //name validation
        //phone number validation
        phoneNumbEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!validation.isValidPhoneNumber(phoneNumbEditText.getText().toString())) {
                    phoneNumbEditText.setError("Invalid phone number...");
                }
            }
        });
        //password validation
        passWordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!validation.isValidPassword(passWordEditText.getText().toString())) {
                    passWordEditText.setError("Password should contain more than five letters...");
                }
            }
        });
        //email validation
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!validation.isValidEmail(emailEditText.getText().toString())) {
                    emailEditText.setError("Invalid email...");
                }
            }
        });
    }

    public void loadGMap(View view) {

        this.hotel.setName(nameEditText.getText().toString());
        this.hotel.setPhoneNumber(phoneNumbEditText.getText().toString());
        this.hotel.setEmail(emailEditText.getText().toString());

        LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
        obj.put("hotel", hotel);
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", obj);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onSignUp(View view) {

        String name = nameEditText.getText().toString();
        String phoneNumber = phoneNumbEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passWordEditText.getText().toString();
        String location = locationEditText.getText().toString();

        if(!this.locationName.equals("")&&this.validation.isValidPhoneNumber(phoneNumber)&&this.validation.isValidPassword(password)&&this.validation.isValidEmail(email)){
            HotelController hotelController = new HotelController(this);
            String lat = Double.toString(this.lat);
            String lng = Double.toString(this.lng);
            boolean signUp = hotelController.suinUp(name, phoneNumber, password, email, lat , lng ,locationName);
            if(signUp){
                Intent intent = new Intent(this , LogInActivity.class);
                intent.putExtra("massage", "hotel");
                startActivity(intent);
            }
        }else {
            Toast.makeText(getApplicationContext(), "Please correct invalid inputs", Toast.LENGTH_LONG).show();
        }
    }
}
