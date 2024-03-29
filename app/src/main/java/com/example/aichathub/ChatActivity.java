package com.example.aichathub;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    RecyclerView rv;
    ImageView back_img;
    TextView chat_header;
    EditText text_msg;
    ConstraintLayout send;
    ArrayList<JSONObject> adapter_data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rv = findViewById(R.id.chat_rv);
        back_img = findViewById(R.id.back_btn);
        chat_header = findViewById(R.id.chat_header_txt);
        text_msg = findViewById(R.id.editText);
        send = findViewById(R.id.send);

        rv.setLayoutManager(new LinearLayoutManager(this));

        send.setOnClickListener(v->{
            String msg = text_msg.getText().toString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("byUser",true);
                jsonObject.put("msg",msg);
                adapter_data.add(jsonObject);
            }catch (JSONException e){
                e.printStackTrace();
            }
            if(msg.isEmpty()){
                text_msg.setError("Cannot be empty");
            }else {
                ChatAdapter adapter = new ChatAdapter(adapter_data);
                rv.setAdapter(adapter);
            }
        });

        back_img.setOnClickListener(v->{
            onBackPressed();
        });

    }
}