package com.smartlogic.saranga.reserveme.CommonViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.Controllers.HotelController;
import com.smartlogic.saranga.reserveme.CustomerViews.CustomerMainActivity;
import com.smartlogic.saranga.reserveme.Controllers.CustomerController;
import com.smartlogic.saranga.reserveme.CustomerViews.CustomerSignUpActivity;
import com.smartlogic.saranga.reserveme.HotelViews.HotelMainActivity;
import com.smartlogic.saranga.reserveme.HotelViews.HotelSignUpActivity;
import com.smartlogic.saranga.reserveme.Models.Customer;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.R;
import com.smartlogic.saranga.reserveme.Validation;

import java.util.LinkedHashMap;

public class LogInActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signUpButton;
    private String accountType;
    private ImageView accountIconImageView;
    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        accountIconImageView = (ImageView) findViewById(R.id.accountImageButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        Bundle extras = getIntent().getExtras();
        accountType = extras.getString("massage");

        if (accountType.equals("customer")) {
            accountIconImageView.setImageResource(R.drawable.user_name_icon);
        } else {
            accountIconImageView.setImageResource(R.drawable.hotel_name_icon);
        }

        validation = new Validation();
        //phone number validation
        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!validation.isValidPhoneNumber(phoneNumberEditText.getText().toString())) {
                    phoneNumberEditText.setError("Invalid phone number...");
                }
            }
        });

        //password validation
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!validation.isValidPassword(passwordEditText.getText().toString())){
                    passwordEditText.setError("Password should contain more than five letters...");
                }
            }
        });
    }

    public void onSignUp(View view) {
        if (accountType.equals("customer")) {
            Intent intent = new Intent(this, CustomerSignUpActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HotelSignUpActivity.class);
            startActivity(intent);
        }
    }

    public void onSignIn(View view) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        if (this.validation.isValidPhoneNumber(phoneNumber) && this.validation.isValidPassword(password)) {
            if (this.accountType.equals("customer")) {
                CustomerController customerController = new CustomerController(this);
                Customer customer = customerController.signIn(phoneNumber, password, accountType);
                if (customer != null) {
                    LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put("customer", customer);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("details", linkedHashMap);
                    Intent intent = new Intent(this, CustomerMainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            } else {

                HotelController hotelController = new HotelController(this);
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Trying to log...");
                dialog.show();
                Hotel hotel = hotelController.signIn(phoneNumber, password, accountType);
                if (hotel != null) {
                    LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put("hotel", hotel);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("details", linkedHashMap);
                    Intent intent = new Intent(this, HotelMainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }dialog.dismiss();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please correct invalid inputs", Toast.LENGTH_LONG).show();
        }
    }
}
