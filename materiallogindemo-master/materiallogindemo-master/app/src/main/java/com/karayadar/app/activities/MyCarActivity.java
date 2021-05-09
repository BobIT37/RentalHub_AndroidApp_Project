package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karayadar.app.HouseItemResponse;
import com.karayadar.app.ItemResponse;
import com.karayadar.app.MyCarListAdapter;
import com.karayadar.app.MyHouseListAdapter;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.models.Car;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Items;
import com.karayadar.app.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyCarListAdapter adapter;
    private List<Items> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        recyclerView = findViewById(R.id.mycarrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user = SharedPrefManager.getInstance(getApplication()).getUser();

        Call<ItemResponse> call = RetrofitClient.getInstance().getApi().getMyCarItems(
                user.getId()
        );

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                userList = response.body().getItems();
                if(userList.isEmpty()){
                    Toast.makeText(getApplication(), "Search not found!", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = new MyCarListAdapter(getApplication(), userList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {

            }
        });
    }

}
