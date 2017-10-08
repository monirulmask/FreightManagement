package com.tendereasy.freightmanagement.dto;

import java.math.BigDecimal;

/**
 * Created by monir on 10/6/2017.
 */
public class RouteDetailsDTO {
    private Integer sourceID;
    private String sourceName;
    private Integer destinationID;
    private String destinationName;
    private String modeOfTransports;
    private Integer containerSize;
    private BigDecimal cost;

    public Integer getSourceID() {
        return sourceID;
    }

    public void setSourceID(Integer sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(Integer destinationID) {
        this.destinationID = destinationID;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getModeOfTransports() {
        return modeOfTransports;
    }

    public void setModeOfTransports(String modeOfTransports) {
        this.modeOfTransports = modeOfTransports;
    }

    public Integer getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(Integer containerSize) {
        this.containerSize = containerSize;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
