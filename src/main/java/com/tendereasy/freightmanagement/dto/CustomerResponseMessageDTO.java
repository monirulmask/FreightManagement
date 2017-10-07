package com.tendereasy.freightmanagement.dto;

import java.util.List;

/**
 * Created by monir on 10/6/2017.
 */
public class CustomerResponseMessageDTO {
    private String message;
    private List<RouteDetailsDTO> results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RouteDetailsDTO> getResults() {
        return results;
    }

    public void setResults(List<RouteDetailsDTO> results) {
        this.results = results;
    }
}
