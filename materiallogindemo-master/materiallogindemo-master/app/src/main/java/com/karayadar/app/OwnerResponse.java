package com.karayadar.app;

import com.karayadar.app.models.HouseModel;

import java.util.List;

public class OwnerResponse {

    private String error;
    private List<HouseModel> items;

    public OwnerResponse(String error, List<HouseModel> items) {
        this.error = error;
        this.items = items;
    }

    public String isError() {
        return error;
    }

    public List<HouseModel> getItems() {
        return items;
    }
}
