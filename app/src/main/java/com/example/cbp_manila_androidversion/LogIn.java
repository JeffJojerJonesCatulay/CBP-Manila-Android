package com.example.cbp_manila_androidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {
    // EditText
    EditText usernameInput, passwordInput;

    // Button
    Button logInBtn, signUpBtn;

    // Progress Bar
    ProgressBar loading;

    // Authentication
    private FirebaseAuth mAuth;

    // Firebase
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    // String
    String emailVal, passwordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // EditText
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        // Button
        logInBtn = findViewById(R.id.logInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        // Progress Bar
        loading = findViewById(R.id.loading);

        // Authentication
        mAuth = FirebaseAuth.getInstance();

        // On Create
        logInOption();
        signUpOption();

        //Temp
        usernameInput.setText("catulaygeorge@gmail.com");
        passwordInput.setText("fabolousboys");

    }

    public void signUpOption(){
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        SignUp.class));
                finish();
            }
        });
    }

    public void logInOption(){
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                emailVal = usernameInput.getText().toString();
                passwordVal = passwordInput.getText().toString();

                if ((usernameInput.getText().toString().isEmpty()) || (passwordInput.getText().toString().isEmpty())){
                    if (usernameInput.getText().toString().isEmpty()){
                        usernameInput.setError("Email is Required");
                        loading.setVisibility(View.GONE);
                    }
                    if (passwordInput.getText().toString().isEmpty()){
                        passwordInput.setError("Password is Required");
                        loading.setVisibility(View.GONE);
                    }
                }
                else {
                    mAuth.signInWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user.isEmailVerified()){
                                    Intent intent = new Intent(LogIn.this, HomeScreenBrowse.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else {
                                    user.sendEmailVerification();
                                    showMessage("Email Verification Required", "Please check your email to verify your account");
                                    loading.setVisibility(View.GONE);
                                }
                            }
                            else {
                                showMessage("Error!", "Please make sure that the email is registered. If registered please check the Email and Password");
                                loading.setVisibility(View.GONE);
                            }
                        }
                    });
                }
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