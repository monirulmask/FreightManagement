package com.tendereasy.freightmanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by monir on 10/9/2017.
 */
public class ResponseRouteDetailsDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String from;
    private String to;
    private String transportType;
    private BigDecimal cost;
    private Integer duration;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
