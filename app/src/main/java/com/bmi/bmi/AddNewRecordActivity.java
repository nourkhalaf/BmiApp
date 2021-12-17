package com.bmi.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bmi.bmi.Prevalent.UserPrevalent;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddNewRecordActivity extends AppCompatActivity {

    private ElegantNumberButton weight, length;
    private EditText date, time;
    private Button saveBtn;
    private DatabaseReference RecordsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);


        RecordsRef = FirebaseDatabase.getInstance().getReference().child("Records");

        weight = findViewById(R.id.add_record_weight);
        length = findViewById(R.id.add_record_length);
        date = findViewById(R.id.add_record_date);
        time = findViewById(R.id.add_record_time);

        saveBtn = findViewById(R.id.add_record_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    private void saveData() {
        String recordDate = date.getText().toString();
        String recordTime = time.getText().toString();

        if (TextUtils.isEmpty(recordDate))
        {
            Toast.makeText(this, getString(R.string.toast_add_date), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(recordTime))
        {
            Toast.makeText(this, getString(R.string.toast_add_time), Toast.LENGTH_SHORT).show();
        }
        else
        {


            double bmi = CalculateBMI.CalcBMI(weight.getNumber(),length.getNumber());
            String bmiStatus = CalculateBMI.CalcBMIStatus(bmi);

            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("weight", weight.getNumber());
            itemMap.put("length", length.getNumber());
            itemMap.put("date", recordDate);
            itemMap.put("time", recordTime);
            itemMap.put("status", bmiStatus);
            itemMap.put("bmi", String.valueOf(bmi));

            RecordsRef.child(UserPrevalent.email).updateChildren(itemMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {

                            }
                            else
                            {

                            }
                        }
                    });


        }
    }

}