package com.smartlogic.saranga.reserveme.Models;

/**
 * Created by sranga on 6/14/2016.
 */
public class Booking {
    private int booking_id;
    private int room_id;
    private int hotel_id;
    private int customer_id;
    private String start_date;
    private String end_date;
    private double charge;
    private String customer_name;
    private String customer_phone_number;
    private String customer_email;
    private Hotel hotel;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomer_phone_number(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public double getCharge() {
        return charge;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public String getCustomer_email() {
        return customer_email;
    }
}
