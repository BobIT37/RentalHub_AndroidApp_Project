package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karayadar.app.HouseItemResponse;
import com.karayadar.app.MyHouseListAdapter;
import com.karayadar.app.MyShopListAdapter;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.ShopItemResponse;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Shops;
import com.karayadar.app.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHouseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyHouseListAdapter adapter;
    private List<HouseModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_house);

        recyclerView = findViewById(R.id.myhouserecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user = SharedPrefManager.getInstance(getApplication()).getUser();

        Call<HouseItemResponse> call = RetrofitClient.getInstance().getApi().getMyHouseItems(
                user.getId()
        );

        call.enqueue(new Callback<HouseItemResponse>() {
            @Override
            public void onResponse(Call<HouseItemResponse> call, Response<HouseItemResponse> response) {

                userList = response.body().getItems();
                if(userList.isEmpty()){
                    Toast.makeText(getApplication(), "Search not found!", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = new MyHouseListAdapter(getApplication(), userList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<HouseItemResponse> call, Throwable t) {

            }
        });
    }

}
