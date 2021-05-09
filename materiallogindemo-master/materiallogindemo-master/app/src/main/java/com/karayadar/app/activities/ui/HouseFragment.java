package com.karayadar.app.activities.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karayadar.app.CarsAdapter;
import com.karayadar.app.HouseAdapter;
import com.karayadar.app.HouseItemResponse;
import com.karayadar.app.ItemResponse;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.ShopAdapter;
import com.karayadar.app.ShopItemResponse;
import com.karayadar.app.activities.GoogleMapShow;
import com.karayadar.app.activities.ui.tools.ToolsViewModel;
import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Items;
import com.karayadar.app.models.Shops;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    private RecyclerView recyclerView;
    private HouseAdapter adapter;
    private List<HouseModel> userList;
    public static ArrayList<String> usArray = new ArrayList<>();

    String choice;
    String range1,range2;
    String[] range ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hous, container, false);

        toolsViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewhouse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemdata();
        SearchView search= view.findViewById(R.id.searchHouse);
        Button filter = view.findViewById(R.id.sliderHouse);

        view.findViewById(R.id.maplocator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userList.isEmpty()){
                HouseModel[] itemsArray = new HouseModel[userList.size()];
                itemsArray = userList.toArray(itemsArray);

                for(HouseModel s : itemsArray){
                    usArray.add(s.getaddress());
                }
                Intent intent = new Intent(getContext(), GoogleMapShow.class);
                intent.putStringArrayListExtra("list",usArray);
                startActivity(intent);}
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] PriceItems = {"1000-5000","5100-10000","11000-20000","21000-30000","31000-50000"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Price Range");

                builder.setSingleChoiceItems(PriceItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice=PriceItems[which];
                        range=choice.split("-");
                        range1=range[0];
                        range2=range[1];



                    }
                });

                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //     Toast.makeText(getContext(), range1+"+"+range2, Toast.LENGTH_SHORT).show();
                        Call<HouseItemResponse> call = RetrofitClient.getInstance().getApi().getHousebyRent(
                                range1,range2
                        );



                        call.enqueue(new Callback<HouseItemResponse>() {
                            @Override
                            public void onResponse(Call<HouseItemResponse> call, Response<HouseItemResponse> response) {


                                userList = response.body().getItems();


                                adapter = new HouseAdapter(getActivity(), userList);
                                recyclerView.setAdapter(adapter);



                            }

                            @Override
                            public void onFailure(Call<HouseItemResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<HouseItemResponse> call = RetrofitClient.getInstance().getApi().gethousebyname(
                        query
                );



                call.enqueue(new Callback<HouseItemResponse>() {
                    @Override
                    public void onResponse(Call<HouseItemResponse> call, Response<HouseItemResponse> response) {

                        userList = response.body().getItems();

                        adapter = new HouseAdapter(getActivity(), userList);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<HouseItemResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) {
                    itemdata();

                } else {
                    Call<HouseItemResponse> call = RetrofitClient.getInstance().getApi().gethousebyname(
                            query
                    );


                    call.enqueue(new Callback<HouseItemResponse>() {
                        @Override
                        public void onResponse(Call<HouseItemResponse> call, Response<HouseItemResponse> response) {

                            userList = response.body().getItems();
                            adapter = new HouseAdapter(getActivity(), userList);
                            recyclerView.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<HouseItemResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                return false;
            }

        });

    }

    public void itemdata()
    {
        Call<HouseItemResponse> call = RetrofitClient.getInstance().getApi().gethouseItems();


        call.enqueue(new Callback<HouseItemResponse>() {
            @Override
            public void onResponse(Call<HouseItemResponse> call, Response<HouseItemResponse> response) {

                userList = response.body().getItems();
                adapter = new HouseAdapter(getActivity(), userList);
                recyclerView.setAdapter(adapter);

                HouseModel[] itemsArray = new HouseModel[userList.size()];
                itemsArray = userList.toArray(itemsArray);




                for(HouseModel s : itemsArray)
                    usArray.add(s.getaddress());

                //Toast.makeText(getContext(), "A:"+usArray, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GoogleMapShow.class);
                intent.putStringArrayListExtra("list",usArray);
              //  startActivity(intent);


            }

            @Override
            public void onFailure(Call<HouseItemResponse> call, Throwable t) {

            }
        });

    }
}
