package com.ufcquixada.meutroco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ufcquixada.meutroco.Views.LoginActivity;
import com.ufcquixada.meutroco.models.LoggedUser;

public class MainActivity extends AppCompatActivity  {

    private LoggedUser loggedUser;
    private static int SPLASH_SCREEN = 2000;
    Animation logo;
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        logo = AnimationUtils.loadAnimation(this, R.anim.logo);
        //hooks
        image = findViewById(R.id.imageView);
        image.setAnimation(logo);

        /*
        * loggedUser.getInstance();
        * Verifica se existe as credenciais do user
        * if exist go to Screen HOME
        * else go to Screen LOGIN
        */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}