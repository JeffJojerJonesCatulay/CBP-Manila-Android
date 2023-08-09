package com.example.cbp_manila_androidversion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrowseResult extends AppCompatActivity {
    DatabaseHelper myDb;

    private ImageView choiceIMG;
    private TextView txt, txtCount;
    private Spinner municipalitiesLIST;
    private EditText searchBox;
    private ListView business_list;
    ArrayList<String> municipalitiesList = new ArrayList<>();
    ArrayList<String> business_Name = new ArrayList<>();
    ArrayList<String> business_Address = new ArrayList<>();
    List<HashMap<String,String>> business;
    SimpleAdapter businessResult;
    String value_category, value_places,searchValue;
    int loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);

        myDb = new DatabaseHelper(this);

        txt = findViewById(R.id.txt);
        choiceIMG = findViewById(R.id.choiceIMG);
        municipalitiesLIST = findViewById(R.id.municipalities);
        searchBox = findViewById(R.id.searchOption);
        business_list = findViewById(R.id.browseRES);
        txtCount =findViewById(R.id.txtCount);

        displaySelectedIMG();
        spinnerChoices_municipalities();
        spinnerChoices_municipalities_Result();
        searchData();
        filterCategory();
        selectedItem_OnListView();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(BrowseResult.this, HomeScreenBrowse.class);
        startActivity(intent);
        finish();
    }

    public void resultData(){
        Intent intent =new Intent(BrowseResult.this, CBPResult.class);
        intent.putExtra("business_Search", searchValue);
        startActivity(intent);
    }

    public void selectedItem_OnListView(){
        business_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchValue = business_list.getItemAtPosition(position).toString();
                resultData();
            }
        });
    }


    public void filterCategory(){
        if (value_category.equals(getString(R.string.com))){
            viewAll_Category_Filter(getString(R.string.com));
        }
        else if (value_category.equals(getString(R.string.food))){
            viewAll_Category_Filter(getString(R.string.food));
        }
        else if (value_category.equals(getString(R.string.furniture))){
            viewAll_Category_Filter(getString(R.string.furniture));
        }
        else if (value_category.equals(getString(R.string.hardware))){
            viewAll_Category_Filter(getString(R.string.hardware));
        }
        else if (value_category.equals(getString(R.string.mall))){
            viewAll_Category_Filter(getString(R.string.mall));
        }
        else if (value_category.equals(getString(R.string.pet))){
            viewAll_Category_Filter(getString(R.string.pet));
        }
        else if (value_category.equals(getString(R.string.salon))){
            viewAll_Category_Filter(getString(R.string.salon));
        }
        else if (value_category.equals(getString(R.string.store))){
            viewAll_Category_Filter(getString(R.string.store));
        }
        else if (value_category.equals(getString(R.string.water))){
            viewAll_Category_Filter(getString(R.string.water));
        }
    }

    public void searchData(){
        try {
            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search(s.toString(), value_category);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void addListViewItem(int loopVal){
        try {
            business = new ArrayList<HashMap<String, String>>();
            String count = String.valueOf(loopVal) + " " + getString(R.string.res);
            txtCount.setText(count);
            for (int x = 0; x < loopVal; x++){
                HashMap<String, String> business_data = new HashMap<String,String>();
                business_data.put("Business_Name", business_Name.get(x));
                business_data.put("Business_Address", business_Address.get(x));
                business.add(business_data);
            }
            String[] from = {
                    "Business_Name","Business_Address",
            };
            int[] to = {
                    R.id.business_name,R.id.business_address
            };

            businessResult = new SimpleAdapter(getBaseContext(),business, R.layout.listview,from,to);
            business_list.setAdapter(businessResult);
        }catch (Exception expetion){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void search(String search, String category){
        try {
            business_Name.clear();
            business_Address.clear();
            Cursor res = myDb.searchOption(search, category);
            if (res.getCount() == 0){
                showMessage("Error" , "Nothing Found");
                return;
            }else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    business_Name.add(res.getString(1));
                    business_Address.add(res.getString(3));
                }
                loop = res.getCount();
                addListViewItem(res.getCount());
            }
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void viewAll_Category_Filter(String category_Choice){
        try {
            Cursor res = myDb.getFilteredData_Category(category_Choice);
            if (res.getCount() == 0){
                showMessage("Error" , "Nothing Found");
                return;
            }else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    business_Name.add(res.getString(1));
                    business_Address.add(res.getString(3));
                }
                loop = res.getCount();
                addListViewItem(res.getCount());
            }
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void viewAll_Category_Filter_Place(String category_Choice, String places_Choice){
        try {
            Cursor res = myDb.getFilteredData_Category_Place(category_Choice, places_Choice);
            if (res.getCount() == 0){
                showMessage("Error" , "Nothing Found");
                return;
            }else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    business_Name.add(res.getString(1));
                    business_Address.add(res.getString(3));
                }
                loop = res.getCount();
                addListViewItem(res.getCount());
            }
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    private void filter_Result_CategoryAndPlaces(){
        business_Name.clear();
        business_Address.clear();
        value_places = municipalitiesLIST.getSelectedItem().toString();
        viewAll_Category_Filter_Place(value_category, value_places);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void spinnerChoices_municipalities_Result(){
        municipalitiesLIST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       viewAll_Category_Filter(value_category);
                       break;
                   case 1:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 2:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 3:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 4:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 5:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 6:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 7:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 8:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 9:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 10:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 11:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 12:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 13:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 14:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 15:
                       filter_Result_CategoryAndPlaces();
                       break;
                   case 16:
                       filter_Result_CategoryAndPlaces();
                       break;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinnerChoices_municipalities(){
        municipalitiesList.add(getString(R.string.noFilter));
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

        ArrayAdapter<String> municipalities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, municipalitiesList );
        municipalities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitiesLIST.setAdapter(municipalities);
    }

    private void displaySelectedIMG(){
        Intent intent = getIntent();
        value_category = intent.getStringExtra("categoryChoice");

        if (value_category.equals(getString(R.string.com))){
            choiceIMG.setImageResource(R.drawable.com);
        }
        else if (value_category.equals(getString(R.string.food))){
            choiceIMG.setImageResource(R.drawable.food);
        }
        else if (value_category.equals(getString(R.string.furniture))){
            choiceIMG.setImageResource(R.drawable.furniture);
        }
        else if (value_category.equals(getString(R.string.hardware))){
            choiceIMG.setImageResource(R.drawable.hardware);
        }
        else if (value_category.equals(getString(R.string.mall))){
            choiceIMG.setImageResource(R.drawable.malls);
        }
        else if (value_category.equals(getString(R.string.pet))){
            choiceIMG.setImageResource(R.drawable.pet);
        }
        else if (value_category.equals(getString(R.string.salon))){
            choiceIMG.setImageResource(R.drawable.salon);
        }
        else if (value_category.equals(getString(R.string.store))){
            choiceIMG.setImageResource(R.drawable.store);
        }
        else if (value_category.equals(getString(R.string.water))){
            choiceIMG.setImageResource(R.drawable.water);
        }
        else {
            choiceIMG.setImageResource(R.drawable.cbplogo);
        }
    }
}