package com.karayadar.app.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.karayadar.app.R;
import com.karayadar.app.ViewPagerAdapter;
import com.karayadar.app.activities.Apartment;
import com.karayadar.app.activities.CarActivity;
import com.karayadar.app.activities.GoogleMapShow;
import com.karayadar.app.activities.HouseActivity;
import com.karayadar.app.activities.MyApartmentsActivity;
import com.karayadar.app.activities.MyCarActivity;
import com.karayadar.app.activities.MyHouseActivity;
import com.karayadar.app.activities.MyShopsActivity;
import com.karayadar.app.fragment.AllFragment;
import com.karayadar.app.fragment.Appartment;
import com.karayadar.app.fragment.Car;
import com.karayadar.app.fragment.House;
import com.karayadar.app.fragment.Shop;
import com.karayadar.app.models.Apartments;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);



        root.findViewById(R.id.carlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MyCarActivity.class));
            }
        });

        root.findViewById(R.id.Apartmentlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyApartmentsActivity.class));
            }
        });
        root.findViewById(R.id.Shoplin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MyShopsActivity.class));
            }
        });

        root.findViewById(R.id.houselin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyHouseActivity.class));
            }
        });













        return root;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new AllFragment());

        adapter.addFragment(new Car());
        adapter.addFragment(new House());
        adapter.addFragment(new Appartment());
        adapter.addFragment(new Shop());
        viewPager.setAdapter(adapter);
    }
}