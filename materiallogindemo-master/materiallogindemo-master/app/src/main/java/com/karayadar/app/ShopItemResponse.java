package com.karayadar.app;

import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.Shops;

import java.util.List;

public class ShopItemResponse {

    private String error;
    private List<Shops> items;

    public ShopItemResponse(String error, List<Shops> items) {
        this.error = error;
        this.items = items;
    }

    public String isError() {
        return error;
    }

    public List<Shops> getItems() {
        return items;
    }
}
