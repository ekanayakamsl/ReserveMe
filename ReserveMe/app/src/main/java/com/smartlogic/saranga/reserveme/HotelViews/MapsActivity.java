package com.smartlogic.saranga.reserveme.HotelViews;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private EditText addressEditText;
    private Address address;
    private double lat=0;
    private double lng=0;
    private String locationName;

    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.addressEditText = (EditText) findViewById(R.id.addressEditText);

        if(getIntent().getExtras()!=null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> getobj  = (HashMap<String, Object>) bn.getSerializable("details");
                this.hotel = (Hotel) getobj.get("hotel");
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng mathale = new LatLng(7.750298, 80.703869);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mathale, 7));

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //create marker
                String cityName = "";
                try {
                    List<Address> addresses = new ArrayList<Address>();
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if(addresses != null && addresses.size()>0) {
                        cityName = addresses.get(0).getLocality();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(cityName);
                googleMap.clear();
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
            }
        });
    }

    public void onSearch(View view) {

        String locationText = this.addressEditText.getText().toString();
        List<Address> addressList = null;
        if (locationText != null || !locationText.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(locationText, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList!=null && addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address.getLocality()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
                this.address = address;
                this.lat = address.getLatitude();
                this.lng = address.getLongitude();
                this.locationName = address.getLocality();
            }
        }
    }

    public void onOk(View view){

        this.hotel.setLat(this.lat);
        this.hotel.setLan(this.lng);
        this.hotel.setLocationName(this.locationName);
        LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
        obj.put("hotel", hotel);
        Intent intent = new Intent(this , HotelSignUpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", obj);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
