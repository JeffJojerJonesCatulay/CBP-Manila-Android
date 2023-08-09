package com.example.cbp_manila_androidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private VideoView introVid;
    private ListView municipal;
    ArrayList<String> municipalitiesList = new ArrayList<>();
    ArrayList<String> businessNumber = new ArrayList<>();
    ArrayList<Integer> municipalPic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        municipal = findViewById(R.id.municipalitiesLIST);

        playVideoIntro();
        bottomNavigation();
        municipalitiesSearch();
        municipalSearchResult();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        System.exit(1);
    }

    public void openSearchResult(String categoryChoiceValue){
        Intent intent = new Intent(SearchActivity.this, SearchResult.class);
        intent.putExtra("categoryChoice", categoryChoiceValue);
        startActivity(intent);
        finish();
    }

    private void municipalSearchResult(){
        municipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        openSearchResult(getString(R.string.binondo));
                        break;
                    case 1:
                        openSearchResult(getString(R.string.ermita));
                        break;
                    case 2:
                        openSearchResult(getString(R.string.intramuros));
                        break;
                    case 3:
                        openSearchResult(getString(R.string.sampaloc));
                        break;
                    case 4:
                        openSearchResult(getString(R.string.paco));
                        break;
                    case 5:
                        openSearchResult(getString(R.string.pandacan));
                        break;
                    case 6:
                        openSearchResult(getString(R.string.portArea));
                        break;
                    case 7:
                        openSearchResult(getString(R.string.quiapo));
                        break;
                    case 8:
                        openSearchResult(getString(R.string.malate));
                        break;
                    case 9:
                        openSearchResult(getString(R.string.sanAndres));
                        break;
                    case 10:
                        openSearchResult(getString(R.string.sanMiguel));
                        break;
                    case 11:
                        openSearchResult(getString(R.string.sanNicholas));
                        break;
                    case 12:
                        openSearchResult(getString(R.string.stAna));
                        break;
                    case 13:
                        openSearchResult(getString(R.string.stCruz));
                        break;
                    case 14:
                        openSearchResult(getString(R.string.stMesa));
                        break;
                    case 15:
                        openSearchResult(getString(R.string.tondo));
                        break;
                }
            }
        });
    }

    private void municipalitiesSearch(){
        municipalPic.add(R.drawable.binondo);
        municipalPic.add(R.drawable.ermita);
        municipalPic.add(R.drawable.intramuros);
        municipalPic.add(R.drawable.sampaloc);
        municipalPic.add(R.drawable.paco);
        municipalPic.add(R.drawable.pandacan);
        municipalPic.add(R.drawable.port_area);
        municipalPic.add(R.drawable.quiapo);
        municipalPic.add(R.drawable.malate);
        municipalPic.add(R.drawable.san_andres);
        municipalPic.add(R.drawable.san_nicholas);
        municipalPic.add(R.drawable.santa_ana);
        municipalPic.add(R.drawable.santa_cruz);
        municipalPic.add(R.drawable.santa_mesa);
        municipalPic.add(R.drawable.tondo);
        municipalPic.add(R.drawable.binondo);

        municipalitiesList.add(getString(R.string.binondo));
        municipalitiesList.add(getString(R.string.ermita));
        municipalitiesList.add(getString(R.string.intramuros));
        municipalitiesList.add(getString(R.string.sampaloc));
        municipalitiesList.add(getString(R.string.paco));
        municipalitiesList.add(getString(R.string.pandacan));
        municipalitiesList.add(getString(R.string.portArea));
        municipalitiesList.add(getString(R.string.quiapo));
        municipalitiesList.add(getString(R.string.malate));
        municipalitiesList.add(getString(R.string.sanAndres));
        municipalitiesList.add(getString(R.string.sanMiguel));
        municipalitiesList.add(getString(R.string.sanNicholas));
        municipalitiesList.add(getString(R.string.stAna));
        municipalitiesList.add(getString(R.string.stCruz));
        municipalitiesList.add(getString(R.string.stMesa));
        municipalitiesList.add(getString(R.string.tondo));
        municipalitiesList.add(getString(R.string.noFilter));

        businessNumber.add("76 Business");
        businessNumber.add("60 Business");
        businessNumber.add("62 Business");
        businessNumber.add("263 Business");
        businessNumber.add("146 Business");

        businessNumber.add("81 Business");
        businessNumber.add("19 Business");
        businessNumber.add("84 Business");
        businessNumber.add("100 Business");
        businessNumber.add("53 Business");

        businessNumber.add("33 Business");
        businessNumber.add("36 Business");
        businessNumber.add("65 Business");
        businessNumber.add("74 Business");
        businessNumber.add("161 Business");

        businessNumber.add("77 Business");



        try {
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();
            for (int x = 0; x < 16; x++){
                HashMap<String, String> hm = new HashMap<String,String>();
                hm.put("Places", municipalitiesList.get(x));
                hm.put("BusinessNumbers",businessNumber.get(x));
                hm.put("Images", Integer.toString(municipalPic.get(x)));
                aList.add(hm);
            }
            String[] from = {
                    "Places","BusinessNumbers", "Images"
            };
            int[] to = {
                    R.id.place,R.id.businessNumber,R.id.img
            };

            SimpleAdapter places = new SimpleAdapter(getBaseContext(),aList, R.layout.listview_places,from,to);
            ListView municipal = (ListView)findViewById(R.id.municipalitiesLIST);
            municipal.setAdapter(places);
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
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
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),
                                HomeScreenBrowse.class));
                        finish();
                        return true;
                    case R.id.nav_search:
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

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}