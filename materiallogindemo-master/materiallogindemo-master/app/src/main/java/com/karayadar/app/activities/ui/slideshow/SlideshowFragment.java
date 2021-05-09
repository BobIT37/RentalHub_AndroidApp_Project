package com.karayadar.app.activities.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karayadar.app.ApartmentAdapter;
import com.karayadar.app.ApartmentItemActivity;
import com.karayadar.app.ApartmentItemResponse;
import com.karayadar.app.CarsAdapter;
import com.karayadar.app.ItemResponse;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.activities.GoogleMapShow;
import com.karayadar.app.models.Apartments;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private RecyclerView recyclerView;
    private ApartmentAdapter adapter;
    private List<Apartments> userList;
    public static ArrayList<String> usArray = new ArrayList<>();

    String choice;
    String range1,range2;
    String[] range ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemdata();
        Button filter = view.findViewById(R.id.sliderApart);
        SearchView search= view.findViewById(R.id.searchApart);

        view.findViewById(R.id.maplocator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userList.isEmpty()){
                Apartments[] itemsArray = new Apartments[userList.size()];
                itemsArray = userList.toArray(itemsArray);

                for(Apartments s : itemsArray){
                    usArray.add(s.getAddress());
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
                        Call<ApartmentItemResponse> call = RetrofitClient.getInstance().getApi().getApartbyRent(
                                range1,range2
                        );



                        call.enqueue(new Callback<ApartmentItemResponse>() {
                            @Override
                            public void onResponse(Call<ApartmentItemResponse> call, Response<ApartmentItemResponse> response) {


                                userList = response.body().getItems();


                                adapter = new ApartmentAdapter(getActivity(), userList);
                                recyclerView.setAdapter(adapter);



                            }

                            @Override
                            public void onFailure(Call<ApartmentItemResponse> call, Throwable t) {
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
                Call<ApartmentItemResponse> call = RetrofitClient.getInstance().getApi().getApartbyname(
                        query
                );



                call.enqueue(new Callback<ApartmentItemResponse>() {
                    @Override
                    public void onResponse(Call<ApartmentItemResponse> call, Response<ApartmentItemResponse> response) {

                        userList = response.body().getItems();

                        adapter = new ApartmentAdapter(getActivity(), userList);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<ApartmentItemResponse> call, Throwable t) {
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
                    Call<ApartmentItemResponse> call = RetrofitClient.getInstance().getApi().getApartbyname(
                            query
                    );


                    call.enqueue(new Callback<ApartmentItemResponse>() {
                        @Override
                        public void onResponse(Call<ApartmentItemResponse> call, Response<ApartmentItemResponse> response) {

                            userList = response.body().getItems();
                            adapter = new ApartmentAdapter(getActivity(), userList);
                            recyclerView.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<ApartmentItemResponse> call, Throwable t) {
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
        Call<ApartmentItemResponse> call = RetrofitClient.getInstance().getApi().getApartmentItems();



        call.enqueue(new Callback<ApartmentItemResponse>() {
            @Override
            public void onResponse(Call<ApartmentItemResponse> call, Response<ApartmentItemResponse> response) {

                userList = response.body().getItems();
                adapter = new ApartmentAdapter(getActivity(), userList);
                recyclerView.setAdapter(adapter);

                Apartments[] itemsArray = new Apartments[userList.size()];
                itemsArray = userList.toArray(itemsArray);




                for(Apartments s : itemsArray){
                    usArray.add(s.getAddress());
                usArray.add(s.getId());}

               // Toast.makeText(getContext(), "A:"+usArray.toArray(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GoogleMapShow.class);
                intent.putStringArrayListExtra("list",usArray);

               // List<String> items = Arrays.asList(usArray.toArray("\\s*,\\s*"));
               // startActivity(intent);


            }

            @Override
            public void onFailure(Call<ApartmentItemResponse> call, Throwable t) {

            }
        });

    }
}
