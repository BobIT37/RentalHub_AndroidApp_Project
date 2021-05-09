package com.karayadar.app;

import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.HouseModel;

import java.util.List;

public class HouseItemResponse {

    private String error;
    private List<HouseModel> items;

    public HouseItemResponse(String error, List<HouseModel> items) {
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
