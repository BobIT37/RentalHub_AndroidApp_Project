package com.karayadar.app.activities.ui.gallery;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import com.karayadar.app.CarsAdapter;
import com.karayadar.app.ItemDisplayActivity;
import com.karayadar.app.ItemResponse;
import com.karayadar.app.R;
import com.karayadar.app.RetrofitClient;
import com.karayadar.app.activities.GoogleMapShow;
import com.karayadar.app.models.Car;
import com.karayadar.app.models.Items;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private CarsAdapter adapter;
    public static List<Items> userList;
    public static ArrayList<String> us;

    String choice;
    String range1,range2;
    String[] range ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemdata();
        Button filter = view.findViewById(R.id.slider);
        SearchView search= view.findViewById(R.id.search);

        us=new ArrayList<>();


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
                        Call<ItemResponse> call = RetrofitClient.getInstance().getApi().getItemsbyRent(
                                range1,range2
                        );



                        call.enqueue(new Callback<ItemResponse>() {
                            @Override
                            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {


                                userList = response.body().getItems();


                                adapter = new CarsAdapter(getActivity(), userList);
                                recyclerView.setAdapter(adapter);



                            }

                            @Override
                            public void onFailure(Call<ItemResponse> call, Throwable t) {
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
                Call<ItemResponse> call = RetrofitClient.getInstance().getApi().getItemsbyname(
                        query
                );



                call.enqueue(new Callback<ItemResponse>() {
                    @Override
                    public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                        userList = response.body().getItems();

                        adapter = new CarsAdapter(getActivity(), userList);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<ItemResponse> call, Throwable t) {
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
                    Call<ItemResponse> call = RetrofitClient.getInstance().getApi().getItemsbyname(
                            query
                    );


                    call.enqueue(new Callback<ItemResponse>() {
                        @Override
                        public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                            userList = response.body().getItems();
                            adapter = new CarsAdapter(getActivity(), userList);
                            recyclerView.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<ItemResponse> call, Throwable t) {
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
        Call<ItemResponse> call = RetrofitClient.getInstance().getApi().getItems();



        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                userList = response.body().getItems();

                adapter = new CarsAdapter(getActivity(), userList);



                Items[] itemsArray = new Items[userList.size()];
                itemsArray = userList.toArray(itemsArray);




                for(Items s : itemsArray)
                    us.add(s.getId());


              //  Toast.makeText(getContext(), "A:"+us, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), GoogleMapShow.class);
                intent.putStringArrayListExtra("list",us);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {

            }
        });

    }
}