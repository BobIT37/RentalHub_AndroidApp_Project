package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karayadar.app.ApartmentItemResponse;
import com.karayadar.app.MyApartmentListAdapter;
import com.karayadar.app.MyShopListAdapter;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.ShopItemResponse;
import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.Shops;
import com.karayadar.app.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyShopsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyShopListAdapter adapter;
    private List<Shops> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shops);

        recyclerView = findViewById(R.id.myshoprecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user = SharedPrefManager.getInstance(getApplication()).getUser();

        Call<ShopItemResponse> call = RetrofitClient.getInstance().getApi().getMyShopsItems(
                user.getId()
        );

        call.enqueue(new Callback<ShopItemResponse>() {
            @Override
            public void onResponse(Call<ShopItemResponse> call, Response<ShopItemResponse> response) {

                userList = response.body().getItems();
                if(userList.isEmpty()){
                    Toast.makeText(getApplication(), "Search not found!", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = new MyShopListAdapter(getApplication(), userList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ShopItemResponse> call, Throwable t) {

            }
        });
    }

}
