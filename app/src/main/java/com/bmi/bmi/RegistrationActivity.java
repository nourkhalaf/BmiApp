package com.bmi.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name, email, password, rePassword;
    private Button registerBtn;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        loadingBar = new ProgressDialog(this);

        name = findViewById(R.id.registration_user_name);
        email = findViewById(R.id.registration_user_email);
        password = findViewById(R.id.registration_user_password);
        rePassword = findViewById(R.id.registration_user_re_password);

        registerBtn = findViewById(R.id.registration_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInfo();

             }
        });
    }

    private void checkInfo() {
        final String userName = name.getText().toString();
        final String userEmail = email.getText().toString();
        final String userPassword = password.getText().toString();
        String userRePassword = rePassword.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, getString(R.string.toast_add_name), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, getString(R.string.toast_add_email), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, getString(R.string.toast_add_password), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userRePassword))
        {
            Toast.makeText(this, getString(R.string.toast_add_re_password), Toast.LENGTH_SHORT).show();
        }
        else if (!userPassword.equals(userRePassword))
        {
            Toast.makeText(this, getString(R.string.toast_check_password), Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle(getString(R.string.loading_register));
            loadingBar.setMessage(getString(R.string.loading_register_message));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(userEmail).exists())
                    {
                        loadingBar.dismiss();
                        Toast.makeText(RegistrationActivity.this, getString(R.string.toast_account_found), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        registerAccount(userName, userEmail, userPassword);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    private void registerAccount(String userName,  String userEmail, String userPassword)
    {
        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("name", userName);
        itemMap.put("email", userEmail);
        itemMap.put("password", userPassword);


        UsersRef.child(userEmail).updateChildren(itemMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Toast.makeText(RegistrationActivity.this, getString(R.string.toast_register_successfully) , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(RegistrationActivity.this,  message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}