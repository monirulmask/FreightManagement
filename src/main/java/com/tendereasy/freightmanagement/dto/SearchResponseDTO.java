package com.tendereasy.freightmanagement.dto;

import java.math.BigDecimal;

/**
 * Created by monir on 11/26/2017.
 */
public class SearchResponseDTO {
    private String fullRoute;
    private String route;
    private BigDecimal cost;
    private Integer duration;

    public String getFullRoute() {
        return fullRoute;
    }

    public void setFullRoute(String fullRoute) {
        this.fullRoute = fullRoute;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
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
