package com.tendereasy.freightmanagement.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by monir on 10/6/2017.
 */
public class SearchCriteriaDTO {
    @NotNull(message = "Source cannot be empty.")
    private String source;
    @NotNull(message = "Destination cannot be empty.")
    private String destination;
    @NotEmpty(message="Atleast one transport mode is required.")
    private List<String> modeOfTransports;
    @NotNull(message = "Container size cannot be empty.")
    private Short containerSize;
    private Short durationFrom;
    private Short durationTo;
    private BigDecimal costFrom;
    private BigDecimal costTo;

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

    public List<String> getModeOfTransports() {
        return modeOfTransports;
    }

    public void setModeOfTransports(List<String> modeOfTransports) {
        this.modeOfTransports = modeOfTransports;
    }

    public Short getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(Short containerSize) {
        this.containerSize = containerSize;
    }

    public Short getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Short durationFrom) {
        this.durationFrom = durationFrom;
    }

    public Short getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Short durationTo) {
        this.durationTo = durationTo;
    }

    public BigDecimal getCostFrom() {
        return costFrom;
    }

    public void setCostFrom(BigDecimal costFrom) {
        this.costFrom = costFrom;
    }

    public BigDecimal getCostTo() {
        return costTo;
    }

    public void setCostTo(BigDecimal costTo) {
        this.costTo = costTo;
    }

    @Override
    public String toString() {
        return "SearchCriteriaDTO{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", modeOfTransports=" + modeOfTransports +
                ", containerSize=" + containerSize +
                ", durationFrom=" + durationFrom +
                ", durationTo=" + durationTo +
                ", costFrom=" + costFrom +
                ", costTo=" + costTo +
                '}';
    }
}
