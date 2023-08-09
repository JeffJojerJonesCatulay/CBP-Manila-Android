package com.example.cbp_manila_androidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenBrowse extends AppCompatActivity {
    private VideoView introVid;
    private ImageView com, food, furniture, hardware, mall, pet, salon, store, water;
    private TextView vidPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_browse);

        com = findViewById(R.id.comIMG);
        food = findViewById(R.id.foodIMG);
        furniture = findViewById(R.id.furnitureIMG);
        hardware = findViewById(R.id.hardwareIMG);
        mall = findViewById(R.id.mallsIMG);
        pet = findViewById(R.id.petIMG);
        salon = findViewById(R.id.salonIMG);
        store = findViewById(R.id.storeIMG);
        water = findViewById(R.id.waterIMG);
        vidPlay = findViewById(R.id.vidBTN);

        playVideoIntro();
        bottomNavigation();
        categorySelection();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        System.exit(1);
    }

    private void playVideoIntro(){
        introVid = findViewById(R.id.cbpVID);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.introvid;
        Uri uri = Uri.parse(videoPath);
        introVid.setVideoURI(uri);
        introVid.start();
        introVid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                introVid.start();
            }
        });
    }

    private void bottomNavigation(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(),
                                SearchActivity.class));
                        finish();
                        return true;
                    case R.id.nav_info:
                        startActivity(new Intent(getApplicationContext(),
                                InfoActivity.class));
                        finish();
                        return true;
                }
                finish();
                return false;
            }
        });
    }

    public  void openBrowseResult(String categoryChoiceValue){
        Intent intent = new Intent(HomeScreenBrowse.this, BrowseResult.class);
        intent.putExtra("categoryChoice", categoryChoiceValue);
        startActivity(intent);
        finish();
    }

    private void categorySelection(){
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.com));
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.food));
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.furniture));
            }
        });

        hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.hardware));
            }
        });

        mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.mall));
            }
        });

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.pet));
            }
        });

        salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.salon));
            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.store));
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowseResult(getString(R.string.water));
            }
        });
    }
}