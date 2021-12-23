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
import android.widget.RadioButton;
import android.widget.Toast;

import com.bmi.bmi.Prevalent.UserPrevalent;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CompleteInfoActivity extends AppCompatActivity {

    private ElegantNumberButton weight, length;
    private EditText DOB;
    private Button saveBtn;
    private String gender;
    private DatabaseReference RecordsRef;
    private ProgressDialog loadingBar;
    private String randomKey, saveCurrentDate, saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);

        RecordsRef = FirebaseDatabase.getInstance().getReference().child("Records");

        loadingBar = new ProgressDialog(this);

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
            loadingBar.setTitle(getString(R.string.loading_add_product_title));
            loadingBar.setMessage(getString(R.string.loading_add_product_message) );
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            String[] items1 = dateOfBirth.split("/");
            String d1=items1[0];
            String m1=items1[1];
            String y1=items1[2];
            int d = Integer.parseInt(d1);
            int m = Integer.parseInt(m1);
            int y = Integer.parseInt(y1);

            String age = getAge(y,m,d);
            calcAgePercent(age);

            saveDataToDatabase();

         }
    }

    private void saveDataToDatabase() {

        double bmi = CalculateBMI.CalcBMI(weight.getNumber(),length.getNumber());
        String bmiStatus = CalculateBMI.CalcBMIStatus(bmi);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        randomKey = saveCurrentDate + saveCurrentTime;


        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", randomKey);
        itemMap.put("weight", weight.getNumber());
        itemMap.put("length", length.getNumber());
        itemMap.put("date", saveCurrentDate);
        itemMap.put("status", bmiStatus);
        itemMap.put("bmi", String.valueOf(bmi));

        RecordsRef.child(UserPrevalent.name).child(randomKey).updateChildren(itemMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Intent intent = new Intent(CompleteInfoActivity.this, HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            loadingBar.dismiss();

                        }
                    }
                });

    }

    private void calcAgePercent(String age) {
        int userAge = Integer.parseInt(age);
        if(userAge >=2 && userAge<=10)
        {
            UserPrevalent.agePercent = 70;
        }
        else if(userAge >10 && userAge<=20)
        {
            if (gender.equals("male"))
                UserPrevalent.agePercent = 90;
            else if (gender.equals("female"))
                UserPrevalent.agePercent = 80;
        }
        else if(userAge >20)
        {
            UserPrevalent.agePercent = 100;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male_radio_btn:
                if (checked)
                    gender = "male";
                    break;

            case R.id.female_radio_btn:
                if (checked)
                    gender = "female";
                    break;
        }
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}