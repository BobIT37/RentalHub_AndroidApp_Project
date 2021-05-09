package com.karayadar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation top, bottom;
    ImageView imageView;
    TextView logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        top = AnimationUtils.loadAnimation(this, R.anim.tp_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imageView = findViewById(R.id.ssimageView);
        logo = findViewById(R.id.slogan_text);
        slogan=findViewById(R.id.again);
        imageView.setAnimation(top);
        logo.setAnimation(bottom);
        slogan.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
//       new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }
}
