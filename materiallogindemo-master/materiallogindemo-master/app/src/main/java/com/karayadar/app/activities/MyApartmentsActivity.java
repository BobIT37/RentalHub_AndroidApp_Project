package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karayadar.app.ApartmentItemResponse;
import com.karayadar.app.MyApartmentListAdapter;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApartmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyApartmentListAdapter adapter;
    private List<Apartments> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apartments);

        recyclerView = findViewById(R.id.myApartmentrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user = SharedPrefManager.getInstance(getApplication()).getUser();

        Call<ApartmentItemResponse> call = RetrofitClient.getInstance().getApi().getMyItems(
                user.getId()
        );

        call.enqueue(new Callback<ApartmentItemResponse>() {
            @Override
            public void onResponse(Call<ApartmentItemResponse> call, Response<ApartmentItemResponse> response) {

                userList = response.body().getItems();
                if(userList.isEmpty()){
                    Toast.makeText(getApplication(), "Search not found!", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter = new MyApartmentListAdapter(getApplication(), userList);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ApartmentItemResponse> call, Throwable t) {

            }
        });
    }

}
