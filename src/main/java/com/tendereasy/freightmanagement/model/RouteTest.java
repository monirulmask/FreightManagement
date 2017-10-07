package com.tendereasy.freightmanagement.model;

import javax.persistence.Entity;

/**
 * Created by monir on 10/7/2017.
 */
public class RouteTest {
    private String source;
    private String destination;
    private String transportMode;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
}
