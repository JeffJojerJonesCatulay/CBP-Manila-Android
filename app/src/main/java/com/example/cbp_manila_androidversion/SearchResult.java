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

public class SearchResult extends AppCompatActivity {
    DatabaseHelper myDb;

    ArrayList<String> category = new ArrayList<>();
    private Spinner categorySelection;
    private ImageView choiceIMG;
    private EditText searchBox;
    private ListView business_list;
    private TextView txtCount;

    ArrayList<String> business_Name = new ArrayList<>();
    ArrayList<String> business_Address = new ArrayList<>();
    List<HashMap<String,String>> business;
    SimpleAdapter businessResult;
    String value_place, value_category, searchValue;
    int loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        myDb = new DatabaseHelper(this);

        business_list = findViewById(R.id.browseRES);
        categorySelection = findViewById(R.id.category);
        choiceIMG = findViewById(R.id.choiceIMG);
        searchBox = findViewById(R.id.searchOption);
        txtCount =findViewById(R.id.txtCount);

        displaySelectedIMG();
        spinnerChoices_category();
        spinnerChoices_Category_Result();
        searchData();
        selectedItem_OnListView();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(SearchResult.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    public void resultData(){
        Intent intent =new Intent(SearchResult.this, CBPResult.class);
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


    public void searchData(){
        try {
            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search(s.toString(), value_place);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void search(String search, String place){
        try {
            business_Name.clear();
            business_Address.clear();
            Cursor res = myDb.searchOption_place(search, place);
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
        value_category = categorySelection.getSelectedItem().toString();
        viewAll_Place_Filter_Category(value_place, value_category);
    }

    private void spinnerChoices_Category_Result(){
        categorySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        viewAll_Place_Filter(value_place);
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void viewAll_Place_Filter(String place_Choice){
        try {
            Cursor res = myDb.getFilteredData_Places(place_Choice);
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

    public void viewAll_Place_Filter_Category(String place_Choice, String category_Choice){
        try {
            Cursor res = myDb.getFilteredData_Places_Category(place_Choice, category_Choice);
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

    private void displaySelectedIMG(){
        Intent intent = getIntent();
        value_place = intent.getStringExtra("categoryChoice");

        if (value_place.equals(getString(R.string.binondo))){
            choiceIMG.setImageResource(R.drawable.binondo);
            viewAll_Place_Filter(getString(R.string.binondo));
        }
        else if (value_place.equals(getString(R.string.ermita))){
            choiceIMG.setImageResource(R.drawable.ermita);
            viewAll_Place_Filter(getString(R.string.ermita));
        }
        else if (value_place.equals(getString(R.string.intramuros))){
            choiceIMG.setImageResource(R.drawable.intramuros);
            viewAll_Place_Filter(getString(R.string.intramuros));
        }
        else if (value_place.equals(getString(R.string.sampaloc))){
            choiceIMG.setImageResource(R.drawable.sampaloc);
            viewAll_Place_Filter(getString(R.string.sampaloc));
        }
        else if (value_place.equals(getString(R.string.paco))){
            choiceIMG.setImageResource(R.drawable.paco);
            viewAll_Place_Filter(getString(R.string.paco));
        }
        else if (value_place.equals(getString(R.string.pandacan))){
            choiceIMG.setImageResource(R.drawable.pandacan);
            viewAll_Place_Filter(getString(R.string.pandacan));
        }
        else if (value_place.equals(getString(R.string.portArea))){
            choiceIMG.setImageResource(R.drawable.port_area);
            viewAll_Place_Filter(getString(R.string.portArea));
        }
        else if (value_place.equals(getString(R.string.quiapo))){
            choiceIMG.setImageResource(R.drawable.quiapo);
            viewAll_Place_Filter(getString(R.string.quiapo));
        }
        else if (value_place.equals(getString(R.string.malate))){
            choiceIMG.setImageResource(R.drawable.malate);
            viewAll_Place_Filter(getString(R.string.malate));
        }
        else if (value_place.equals(getString(R.string.sanAndres))){
            choiceIMG.setImageResource(R.drawable.san_andres);
            viewAll_Place_Filter(getString(R.string.sanAndres));
        }
        else if (value_place.equals(getString(R.string.sanMiguel))){
            choiceIMG.setImageResource(R.drawable.san_miguel);
            viewAll_Place_Filter(getString(R.string.sanMiguel));
        }
        else if (value_place.equals(getString(R.string.sanNicholas))){
            choiceIMG.setImageResource(R.drawable.san_nicholas);
            viewAll_Place_Filter(getString(R.string.sanNicholas));
        }
        else if (value_place.equals(getString(R.string.stAna))){
            choiceIMG.setImageResource(R.drawable.santa_ana);
            viewAll_Place_Filter(getString(R.string.stAna));
        }
        else if (value_place.equals(getString(R.string.stCruz))){
            choiceIMG.setImageResource(R.drawable.santa_cruz);
            viewAll_Place_Filter(getString(R.string.stCruz));
        }
        else if (value_place.equals(getString(R.string.stMesa))){
            choiceIMG.setImageResource(R.drawable.santa_mesa);
            viewAll_Place_Filter(getString(R.string.stMesa));
        }
        else if (value_place.equals(getString(R.string.tondo))){
            choiceIMG.setImageResource(R.drawable.tondo);
            viewAll_Place_Filter(getString(R.string.tondo));
        }
        else {
            choiceIMG.setImageResource(R.drawable.cbplogo);
        }
    }

    private void spinnerChoices_category(){
        category.add(getString(R.string.noFilter));
        category.add(getString(R.string.com));
        category.add(getString(R.string.food));
        category.add(getString(R.string.furniture));
        category.add(getString(R.string.hardware));
        category.add(getString(R.string.mall));
        category.add(getString(R.string.pet));
        category.add(getString(R.string.salon));
        category.add(getString(R.string.store));
        category.add(getString(R.string.water));

        ArrayAdapter<String> categoryList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category );
        categoryList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySelection.setAdapter(categoryList);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}