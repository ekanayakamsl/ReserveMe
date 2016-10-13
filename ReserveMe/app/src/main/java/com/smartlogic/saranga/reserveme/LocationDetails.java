package com.smartlogic.saranga.reserveme;

import java.io.Serializable;

/**
 * Created by sranga on 6/6/2016.
 */
public class LocationDetails implements Serializable {

    private float lat;
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLng() {

        return lng;
    }
}
