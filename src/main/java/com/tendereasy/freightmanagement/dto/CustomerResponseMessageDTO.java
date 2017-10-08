package com.tendereasy.freightmanagement.dto;

import java.util.List;

/**
 * Created by monir on 10/6/2017.
 */
public class CustomerResponseMessageDTO {
    private String message;
    private List<RouteContainer> results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RouteContainer> getResults() {
        return results;
    }

    public void setResults(List<RouteContainer> results) {
        this.results = results;
    }
}
