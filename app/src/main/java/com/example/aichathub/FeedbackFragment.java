package com.example.aichathub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class FeedbackFragment extends Fragment {
    Spinner spinner;
    EditText text;
    Button send_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feedback, container, false);
        spinner = v.findViewById(R.id.type_spinner);
        text = v.findViewById(R.id.textBox);
        send_btn = v.findViewById(R.id.send_button);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_type = spinner.getSelectedItem().toString();
                String txt_msg = text.getText().toString();
                if(txt_msg.isEmpty()){
                    Toast.makeText(getContext(), "Please write something", Toast.LENGTH_SHORT).show();
                    text.setError("Cannot be empty!");
                }else {
                    Toast.makeText(getContext(), "type: "+selected_type+"\n"+"msg: "+txt_msg, Toast.LENGTH_SHORT).show();
                    Log.i("onSend", "type: "+selected_type+"\n"+"msg: "+txt_msg);
                }
            }
        });
        return v;
    }

}