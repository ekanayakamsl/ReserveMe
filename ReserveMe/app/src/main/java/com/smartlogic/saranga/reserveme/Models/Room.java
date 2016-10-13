package com.smartlogic.saranga.reserveme.Models;

import java.io.Serializable;

/**
 * Created by sranga on 6/13/2016.
 */
public class Room  implements Serializable {

    int hotel_id;
    int room_id;
    double charge;
    int capacity;

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public double getCharge() {
        return charge;
    }

    public int getCapacity() {
        return capacity;
    }
}
