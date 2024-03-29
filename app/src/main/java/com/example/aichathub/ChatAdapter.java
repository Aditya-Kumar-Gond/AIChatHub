package com.example.aichathub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    ArrayList<JSONObject> data = new ArrayList<>();
    public ChatAdapter(ArrayList<JSONObject> real_data){
        data = real_data;
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_container_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
            JSONObject obj = data.get(position);
            TextView user_tv = holder.user_response;
            TextView AI_tv = holder.ai_response;
        try {
            String msg = obj.getString("msg");
            Boolean byUser = obj.getBoolean("byUser");

            if(byUser){
                user_tv.setVisibility(View.VISIBLE);
                AI_tv.setVisibility(View.GONE);
                user_tv.setText(msg);
                if(msg.length()>24){
                    user_tv.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                }
            }else {
                user_tv.setVisibility(View.GONE);
                AI_tv.setVisibility(View.VISIBLE);
                AI_tv.setText(msg);
                if(msg.length()>24){
                    AI_tv.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_response,ai_response;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_response = itemView.findViewById(R.id.user_input);
            ai_response = itemView.findViewById(R.id.ai_output);
        }
    }
}
