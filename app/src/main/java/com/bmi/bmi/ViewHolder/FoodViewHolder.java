package com.bmi.bmi.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmi.bmi.Interface.ItemClickListener;
import com.bmi.bmi.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ItemClickListener listener;
    public TextView name, calories, category;
    public ImageView image, delete;
    public Button edit;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.food_item_name);
        calories = itemView.findViewById(R.id.food_item_calory);
        category = itemView.findViewById(R.id.food_item_category);
        image = itemView.findViewById(R.id.food_item_photo);
        delete = itemView.findViewById(R.id.food_item_delete);
        edit = itemView.findViewById(R.id.food_item_edit_btn);


    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
