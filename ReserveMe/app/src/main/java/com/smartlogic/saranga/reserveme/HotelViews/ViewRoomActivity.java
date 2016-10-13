package com.smartlogic.saranga.reserveme.HotelViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.smartlogic.saranga.reserveme.CommonViews.HotelRoomAdapter;
import com.smartlogic.saranga.reserveme.CommonViews.LoadingActivity;
import com.smartlogic.saranga.reserveme.Controllers.RoomController;
import com.smartlogic.saranga.reserveme.Models.Hotel;
import com.smartlogic.saranga.reserveme.Models.Room;
import com.smartlogic.saranga.reserveme.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewRoomActivity extends AppCompatActivity {
    ListView roomListView;
    Hotel hotel;
    EditText capacityTditText;
    EditText chargeditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);
        roomListView = (ListView) findViewById(R.id.roomListView);

        chargeditText = (EditText) findViewById(R.id.chargeditText);
        capacityTditText = (EditText) findViewById(R.id.capacityTditText);

        if (getIntent().getExtras() != null) {
            try {
                Bundle bn = getIntent().getExtras();
                HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
                objectHashMap = (HashMap<String, Object>) bn.getSerializable("details");
                this.hotel = (Hotel) objectHashMap.get("hotel");
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
            roomListView.setAdapter(new HotelRoomAdapter(availabilities ,capacities,charges,roomNumbers,ViewRoomActivity.this));
        } else {
            roomListView.setAdapter(null);
        }
    }

    public void onConfirm(View view){
        RoomController roomController = new RoomController(this);
        String hotel_id = Integer.toString(this.hotel.getId());
        String capacity = capacityTditText.getText().toString();
        String charge = chargeditText.getText().toString();
        roomController.addRoom(hotel_id,capacity,charge);
    }


    public void onSignOut(View view){
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }


}
