package com.karayadar.app;

import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.Items;

import java.util.List;

public class ApartmentItemResponse {

    private String error;
    private List<Apartments> items;

    public ApartmentItemResponse(String error, List<Apartments> items) {
        this.error = error;
        this.items = items;
    }

    public String isError() {
        return error;
    }

    public List<Apartments> getItems() {
        return items;
    }
}
