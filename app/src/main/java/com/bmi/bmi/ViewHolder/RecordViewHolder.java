package com.bmi.bmi.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmi.bmi.Interface.ItemClickListener;
import com.bmi.bmi.R;

public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ItemClickListener listener;
    public TextView weight, length, date, status;

    public RecordViewHolder(@NonNull View itemView) {
        super(itemView);
       weight = itemView.findViewById(R.id.record_weight);
       length = itemView.findViewById(R.id.record_length);
       date = itemView.findViewById(R.id.record_date);
       status = itemView.findViewById(R.id.record_status);


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
