package com.example.cbp_manila_androidversion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CBPResult extends AppCompatActivity {
    DatabaseHelper myDb;

    private TextView business_name_result, business_description_result, business_address_result, business_municipal_result, business_time_result, business_contact_result;
    private TextView business_website_result, business_facebook_result, business_instagram_result, business_announcement_result;
    byte[] business_Img;
    ArrayList<Bitmap> business_Img_Result_= new ArrayList<>();

    String fb_link, instagram_link, website_link;

    ImageView openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_b_p_result);

        myDb = new DatabaseHelper(this);

        openMap = findViewById(R.id.openMap);

        business_name_result = findViewById(R.id.business_Name_Result);
        business_description_result = findViewById(R.id.business_Description_Result);
        business_address_result = findViewById(R.id.business_Address_Result);
        business_municipal_result = findViewById(R.id.business_Municipal_Result);
        business_time_result = findViewById(R.id.business_Time_Result);
        business_contact_result = findViewById(R.id.business_Contact_Result);

        business_website_result = findViewById(R.id.business_Website_Result);
        business_website_result.setPaintFlags(business_website_result.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        business_facebook_result = findViewById(R.id.business_Facebook_Result);
        business_facebook_result.setPaintFlags(business_facebook_result.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        business_instagram_result = findViewById(R.id.business_Insta_Result);
        business_instagram_result.setPaintFlags(business_instagram_result.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        business_announcement_result = findViewById(R.id.business_Announcement_Result);

        business_address_result.setPaintFlags(business_address_result.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        resultFormatting();
        openUrl_Selection();
        openMapActivity();
    }

    public void openMapActivity(){
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CBPResult.this, MapResult.class);
                i.putExtra("Location",business_address_result.getText().toString());
                startActivity(i);
            }
        });

        business_address_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CBPResult.this, MapResult.class);
                i.putExtra("Location",business_address_result.getText().toString());
                startActivity(i);
            }
        });
    }

    private void resultFormatting(){
        Intent intent = getIntent();
        String value = intent.getStringExtra("business_Search");

        String step1 = value.replaceAll("[\\{\\}]", "");
        String step2 = step1.replaceAll("Business_Name=", "");
        String step3 = step2.replaceAll("Business_Address=", "");
        String[] step4 = step3.split(",");

        String finalFormat_Value = step4[0];
        resultDisplay(finalFormat_Value);

    }

    public void resultDisplay(String search_Result){
        try {
            Cursor res = myDb.searchOption_Final_Result(search_Result);
            if (res.getCount() == 0){
                showMessage("Error" , "Nothing Found");
                return;
            }else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    business_name_result.setText(res.getString(1));
                    business_description_result.setText(res.getString(2));
                    business_address_result.setText(res.getString(3));
                    business_municipal_result.setText(res.getString(11));
                    business_time_result.setText(res.getString(4));
                    business_contact_result.setText(res.getString(5));

                    business_website_result.setText(res.getString(6));
                    website_link = res.getString(6);

                    business_facebook_result.setText(res.getString(7));
                    fb_link = res.getString(7);

                    business_instagram_result.setText(res.getString(8));
                    instagram_link = res.getString(8);

                    business_announcement_result.setText(res.getString(9));
                }
            }
        }catch (Exception exception){
            showMessage("Something is Wrong!", "We are still improving CBP, Thank you!");
        }
    }

    public void openUrl_Selection(){
        business_website_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (website_link.isEmpty()){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else if (website_link.equals("Unavailable")){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else {
                    openURL(website_link);
                }
            }
        });

        business_facebook_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fb_link.isEmpty()){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else if (fb_link.equals("Unavailable")){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else {
                    openURL(fb_link);
                }
            }
        });

        business_instagram_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instagram_link.isEmpty()){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else if (instagram_link.equals("Unavailable")){
                    showMessage("Sorry", "Link is Unavailable");
                }
                else {
                    openURL(instagram_link);
                }
            }
        });
    }

    public void openURL(String link_Value){
        Uri uri = Uri.parse(link_Value);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}