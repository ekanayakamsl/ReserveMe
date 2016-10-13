package com.smartlogic.saranga.reserveme.CommonViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smartlogic.saranga.reserveme.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

    }

    public void customerSignIn(View view){

        Intent intent = new Intent(this , LogInActivity.class);
        intent.putExtra("massage", "customer");
        startActivity(intent);
    }

    public void hotelSignIn(View view){
        Intent intent = new Intent(this , LogInActivity.class);
        intent.putExtra("massage", "hotel");
        startActivity(intent);
    }
}