package com.smartlogic.saranga.reserveme.CustomerViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.smartlogic.saranga.reserveme.CommonViews.LogInActivity;
import com.smartlogic.saranga.reserveme.Controllers.CustomerController;
import com.smartlogic.saranga.reserveme.R;
import com.smartlogic.saranga.reserveme.Validation;

public class CustomerSignUpActivity extends AppCompatActivity {


    private EditText phoneNumbText;
    private EditText emailText;
    private EditText passWordText;
    private EditText nameText;
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_un);

        nameText = (EditText) findViewById(R.id.nameEditText);
        phoneNumbText = (EditText) findViewById(R.id.phoneNumberEditText);
        emailText = (EditText) findViewById(R.id.emailEditText);
        passWordText = (EditText) findViewById(R.id.passwordEditText);

        validation = new Validation();
        //name validation

        //phone number validation
        phoneNumbText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!validation.isValidPhoneNumber(phoneNumbText.getText().toString())) {
                    phoneNumbText.setError("Invalid phone number...");
                }
            }
        });
        //password validation
        passWordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!validation.isValidPassword(passWordText.getText().toString())){
                    passWordText.setError("Password should contain more than five letters...");
                }
            }
        });
        //email validation
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!validation.isValidEmail(emailText.getText().toString())){
                    emailText.setError("Invalid email...");
                }
            }
        });

    }

    public void onSignUp(View view){

        String name = nameText.getText().toString();
        String phonenumber = phoneNumbText.getText().toString();
        String email = emailText.getText().toString();
        String password = passWordText.getText().toString();

        if(this.validation.isValidPhoneNumber(phonenumber)&&this.validation.isValidPassword(password)&&this.validation.isValidEmail(email)){
            CustomerController customerController = new CustomerController(this);
            boolean signUp = customerController.suinUp(name,phonenumber,password,email);
            if(signUp){
                Intent intent = new Intent(this , LogInActivity.class);
                intent.putExtra("massage", "customer");
                startActivity(intent);
            }
        }else {
            Toast.makeText(getApplicationContext(), "Please correct invalid inputs", Toast.LENGTH_LONG).show();
        }
    }
}