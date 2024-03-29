package com.example.aichathub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    ArrayList<JSONObject> data = new ArrayList<>();
    public DataAdapter(ArrayList<JSONObject> real_data){
        data = real_data;
    }

    public DataAdapter(){

    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_chat_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addIcon = itemView.findViewById(R.id.add_icon);
            
            addIcon.setOnClickListener(v -> {
                Context context = v.getContext();
                context.startActivity(new Intent(context,ChatActivity.class));
                Toast.makeText(context, "clicked!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
