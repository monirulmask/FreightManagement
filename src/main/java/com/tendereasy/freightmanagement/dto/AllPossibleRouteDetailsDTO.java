package com.tendereasy.freightmanagement.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by monir on 10/10/2017.
 */
public class AllPossibleRouteDetailsDTO {
    private List<ResponseRouteDetailsDTO> route;
    private BigDecimal totalCost;
    private Integer totalDuration;

    public List<ResponseRouteDetailsDTO> getRoute() {
        return route;
    }

    public void setRoute(List<ResponseRouteDetailsDTO> route) {
        this.route = route;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }
}
