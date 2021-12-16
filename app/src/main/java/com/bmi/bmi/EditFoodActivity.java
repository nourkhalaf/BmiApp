package com.bmi.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class EditFoodActivity extends AppCompatActivity {

    private String Name, Calories, Category, foodId;
    private String productRandomKey, saveCurrentDate, saveCurrentTime, downloadImageUrl;
    private EditText name, calories;
    private Button saveBtn, uploadPhotoBtn;
    private ImageView InputImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private Spinner categories;

    private StorageReference FoodsImagesRef;
    private DatabaseReference FoodsRef;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        foodId = getIntent().getStringExtra("foodId");
        FoodsRef = FirebaseDatabase.getInstance().getReference().child("Foods").child(foodId);

    }
}