package com.karayadar.app;

import com.karayadar.app.models.Items;

import java.util.List;

public class ItemResponse {

    private String error;
    private List<Items> items;

    public ItemResponse(String error, List<Items> items) {
        this.error = error;
        this.items = items;
    }

    public String isError() {
        return error;
    }

    public List<Items> getItems() {
        return items;
    }
}
