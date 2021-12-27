package com.bmi.bmi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddNewRecordActivity extends AppCompatActivity {

    private ElegantNumberButton weight, length;
    private EditText date, time;
    private Button saveBtn;
    private DatabaseReference RecordsRef;
    private String randomKey, saveCurrentDate, saveCurrentTime, previousBmi="";

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
                RecordsRef.child(UserPrevalent.name).orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        previousBmi = snapshot.child("bmi").getValue().toString();

                        Toast.makeText(AddNewRecordActivity.this,previousBmi, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

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

            Calendar calendar = Calendar.getInstance();


            Toast.makeText(AddNewRecordActivity.this,"1 = "+bmiStatus,Toast.LENGTH_SHORT).show();
            Toast.makeText(AddNewRecordActivity.this,"2 = "+String.valueOf(bmi),Toast.LENGTH_SHORT).show();
            Toast.makeText(AddNewRecordActivity.this,"3 = "+previousBmi,Toast.LENGTH_SHORT).show();

           // String currentStatus = CalculateBMI.calcCurrentStatus(bmiStatus,String.valueOf(bmi),previousBmi);


            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            randomKey = saveCurrentDate + saveCurrentTime;

            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", randomKey);
            itemMap.put("weight", weight.getNumber());
            itemMap.put("length", length.getNumber());
            itemMap.put("date", recordDate);
            itemMap.put("time", recordTime);
            itemMap.put("status", bmiStatus);
            itemMap.put("bmi", String.valueOf(bmi));

            RecordsRef.child(UserPrevalent.name).child(randomKey).updateChildren(itemMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AddNewRecordActivity.this, getString(R.string.toast_add_record),Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(AddNewRecordActivity.this,HomeActivity.class);
//
//                                intent.putExtra("currentStatus",currentStatus);
//                                startActivity(intent);
//                                finish();


                            }
                            else
                            {

                            }
                        }
                    });


        }
    }

}