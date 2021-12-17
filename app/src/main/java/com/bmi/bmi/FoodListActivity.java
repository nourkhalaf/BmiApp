package com.bmi.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bmi.bmi.Model.Food;
import com.bmi.bmi.Model.Record;
import com.bmi.bmi.Prevalent.UserPrevalent;
import com.bmi.bmi.ViewHolder.FoodViewHolder;
import com.bmi.bmi.ViewHolder.RecordViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodListActivity extends AppCompatActivity {

    private DatabaseReference FoodsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        FoodsRef = FirebaseDatabase.getInstance().getReference().child("Foods").child(UserPrevalent.email);

        recyclerView = findViewById(R.id.food_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(FoodsRef, Food.class)
                        .build();

        FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter =
                new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@androidx.annotation.NonNull FoodViewHolder holder, int position, @androidx.annotation.NonNull final Food model) {
                        holder.name.setText(model.getName());
                        holder.category.setText(model.getCategory());
                        holder.calories.setText(model.getCalories()+" cal/g");
                        Picasso.get().load(model.getImage()).into(holder.image);
                        
                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(FoodListActivity.this, EditFoodActivity.class);
                                intent.putExtra("foodId",model.getId());
                                startActivity(intent);
                            }
                        });

                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteItem(model.getId());
                            }
                        }); 

                    }


                    @androidx.annotation.NonNull
                    @Override
                    public FoodViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
                        FoodViewHolder holder = new FoodViewHolder(view);
                        return holder;
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteItem(String id) {
        FoodsRef.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(FoodListActivity.this,getString(R.string.toast_delete),Toast.LENGTH_SHORT).show();
            }
        });
    }


}