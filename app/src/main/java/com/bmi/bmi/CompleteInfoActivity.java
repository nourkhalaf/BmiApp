package com.bmi.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class CompleteInfoActivity extends AppCompatActivity {

    private ElegantNumberButton weight, length;
    private EditText DOB;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);


        weight = findViewById(R.id.complete_info_weight);
        length = findViewById(R.id.complete_info_length);
        DOB = findViewById(R.id.complete_info_DOB);

        saveBtn = findViewById(R.id.complete_info_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    private void saveData() {
        String dateOfBirth = DOB.getText().toString();
        if (TextUtils.isEmpty(dateOfBirth))
        {
            Toast.makeText(this, getString(R.string.toast_add_dob), Toast.LENGTH_SHORT).show();
        }
        else
        {

        }
    }
}