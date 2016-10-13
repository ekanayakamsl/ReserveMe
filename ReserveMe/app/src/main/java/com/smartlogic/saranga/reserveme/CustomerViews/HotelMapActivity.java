package com.smartlogic.saranga.reserveme.CustomerViews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartlogic.saranga.reserveme.Controllers.MapController;
import com.smartlogic.saranga.reserveme.Models.Customer;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class HotelMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().getExtras() != null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
                objectHashMap = (HashMap<String, Object>) bn.getSerializable("details");
                Customer customer = (Customer) objectHashMap.get("customer");
                this.customer = customer;
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        LatLng mathale = new LatLng(7.750298, 80.703869);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mathale, 7));
    }

    public void onSearch(View view) {

        EditText locationText = (EditText) findViewById(R.id.addressEditText);
        String Laddress = locationText.getText().toString();
        List<Address> addressList = null;

        if (locationText != null || !locationText.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(Laddress, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title(address.getLocality()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));

                final MapController mapController = new MapController(this);
                final LinkedList<Hotel> hotels = mapController.getNearestHotels(Double.toString(latLng.latitude), Double.toString(latLng.longitude));

                for (final Hotel hotel : hotels) {
                    LatLng latLng1 = new LatLng(hotel.getLat(), hotel.getLan());
                    MarkerOptions markerOptions = new MarkerOptions();
                    googleMap.addMarker(new MarkerOptions().position(latLng1).title("" + hotel.getId()));
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            int id = Integer.parseInt(marker.getTitle());
                            Hotel  clickedHotel = null;
                            for (Hotel hotel : hotels) {
                                if (hotel.getId() == id) {
                                    clickedHotel = hotel;
                                }
                            }
                            if (clickedHotel != null) {
                                final Hotel h =clickedHotel;
                                AlertDialog.Builder builder = new AlertDialog.Builder(HotelMapActivity.this);
                                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button
                                        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
                                        linkedHashMap.put("customer", HotelMapActivity.this.customer);
                                        linkedHashMap.put("hotel", h);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("details", linkedHashMap);
                                        Intent intent = new Intent(HotelMapActivity.this, ViewHotelActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });
                                builder.setMessage(h.getLocationName())
                                        .setTitle(h.getName());
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            return false;
                        }
                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onOk(View view) {
    }
}
