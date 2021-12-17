package com.bmi.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bmi.bmi.Model.Record;
import com.bmi.bmi.Prevalent.UserPrevalent;
import com.bmi.bmi.ViewHolder.RecordViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference RecordsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Button addFoodBtn, addRecordBtn, viewFoodBtn;
    private TextView currentStatus;

    private String status1, status2, bmi1, bmi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecordsRef = FirebaseDatabase.getInstance().getReference().child("Records").child(UserPrevalent.email);

        currentStatus = findViewById(R.id.current_state);

        addFoodBtn = findViewById(R.id.home_add_food_btn);
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AddFoodDetailsActivity.class);
                startActivity(intent);
            }
        });

        addRecordBtn = findViewById(R.id.home_add_record_btn);
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AddNewRecordActivity.class);
                startActivity(intent);
            }
        });

        viewFoodBtn = findViewById(R.id.home_view_food_btn);
        viewFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        recyclerView = findViewById(R.id.old_status_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Record> options =
                new FirebaseRecyclerOptions.Builder<Record>()
                        .setQuery(RecordsRef, Record.class)
                        .build();

        FirebaseRecyclerAdapter<Record, RecordViewHolder> adapter =
                new FirebaseRecyclerAdapter<Record, RecordViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@androidx.annotation.NonNull RecordViewHolder holder, int position, @androidx.annotation.NonNull Record model) {
                        holder.weight.setText(model.getTime());
                        holder.length.setText(model.getLength());
                        holder.date.setText(model.getDate());
                        holder.status.setText(model.getStatus());


                    }


                    @androidx.annotation.NonNull
                    @Override
                    public RecordViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
                        RecordViewHolder holder = new RecordViewHolder(view);
                        return holder;
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

        Query lastQuery = RecordsRef.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                status1 = dataSnapshot.child("status").getValue().toString();
                bmi1 = dataSnapshot.child("bmi").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        Query lastQuery2 = RecordsRef.orderByKey().limitToLast(2);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                status2 = dataSnapshot.child("status").getValue().toString();
                bmi2 = dataSnapshot.child("bmi").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        if(!bmi2.isEmpty()){
        String status = CalculateBMI.calcCurrentStatus(status1,bmi1,bmi2);
        currentStatus.setText(status1+" ( "+status+" )");}
        else {
            currentStatus.setText(status1);
        }

    }





}