package com.bmi.bmi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bmi.bmi.Model.User;
import com.bmi.bmi.Prevalent.UserPrevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private EditText name, password;
    private Button loginBtn;
    private TextView register;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        loadingBar = new ProgressDialog(this);


        name = findViewById(R.id.login_user_name);
        password = findViewById(R.id.login_user_password);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInfo();
            }
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void checkInfo() {

        final String userName = name.getText().toString();
        final String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, getString(R.string.toast_add_name), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, getString(R.string.toast_add_password), Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle(getString(R.string.loading_register));
            loadingBar.setMessage(getString(R.string.loading_register_message));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            allowAccess(userName,userPassword);

        }
    }

    private void allowAccess(final String userName, final String userPassword) {
       UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(userName).exists())
                {
                   User userData = dataSnapshot.child(userName).getValue(User.class);

                    assert userData != null;
                    if (userData.getName().equals(userName))
                    {
                        if (userData.getPassword().equals(userPassword))
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this,getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show();

                            UserPrevalent.name = userName;
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, getString(R.string.toast_login_wrong_password), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, getString(R.string.toast_login_not_found), Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}