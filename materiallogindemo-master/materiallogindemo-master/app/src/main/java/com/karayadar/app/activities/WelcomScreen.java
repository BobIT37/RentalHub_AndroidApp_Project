package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.karayadar.app.LoginActivity;
import com.karayadar.app.R;

public class WelcomScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);
    }

    public void Login(View view) {
        startActivity(new Intent(WelcomScreen.this, LoginActivity.class));
    }

    public void facebook(View view) {
    }

    public void google(View view) {
    }
}
