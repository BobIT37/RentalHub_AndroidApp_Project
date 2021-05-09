package com.karayadar.app;

import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.RequiresApi;

import com.karayadar.app.fragment.AllFragment;
import com.karayadar.app.fragment.Appartment;
import com.karayadar.app.fragment.Car;
import com.karayadar.app.fragment.House;
import com.karayadar.app.fragment.Shop;

public class Home extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("mycustomdialog", true);
//        if (firstrun) {
//            ScreenShotTerms();
//        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AllFragment());

        adapter.addFragment(new Car());
        adapter.addFragment(new House());
        adapter.addFragment(new Appartment());
        adapter.addFragment(new Shop());
        viewPager.setAdapter(adapter);
    }

    }
