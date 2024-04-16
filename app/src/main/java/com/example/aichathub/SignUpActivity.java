package com.example.aichathub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,password,age;
    Button createAccount;
    TextView toLogin;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("users_detail");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.EmailId);
        password = findViewById(R.id.pwd);
        age = findViewById(R.id.age);
        createAccount = findViewById(R.id.button1);
        toLogin = findViewById(R.id.toLogin);
        
        
        createAccount.setOnClickListener(v ->{
            String full_name = name.getText().toString();
            String age_str = age.getText().toString();
            String email_str = email.getText().toString();
            String pwd = password.getText().toString().trim();

            if(name.getText().toString().isEmpty()){
                validateData();
            }else if(age.getText().toString().isEmpty()){
                validateData();
            }else if(email.getText().toString().isEmpty()){
                validateData();
            }else if(password.getText().toString().isEmpty()){
                validateData();
            }else if (password.getText().toString().length()<8){
                validateData();
            }else {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Creating account...");
                dialog.show();
                auth.createUserWithEmailAndPassword(email_str,pwd).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        Map<String,String> data = new HashMap<>();
                        data.put("Full_name",full_name);
                        data.put("Age",age_str);
                        data.put("Email",email_str);
                        data.put("Password",pwd);

                        ref.child(auth.getCurrentUser().getUid()).setValue(data).addOnSuccessListener(unused -> {
                            dialog.dismiss();
                            auth.signOut();
                            Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,LoginActivityActivity.class));
                        });
                    }else {
                      //  email.setError("Email already registered.");
                        email.setText("");
                        dialog.dismiss();
                        email.setFocusable(true);
                        Toast.makeText(SignUpActivity.this, "already registered", Toast.LENGTH_SHORT).show();
                    }
                });
                
                
                
                
                
                
            }
            
            

        });
    }

    private void validateData() {
        if(name.getText().toString().isEmpty()){
            name.setError("Please enter your name.");
        }

        if(age.getText().toString().isEmpty()){
            age.setError("Please enter your age.");
        }

        if(email.getText().toString().isEmpty()){
            email.setError("Please provide valid email.");
        }

        if(password.getText().toString().isEmpty()){
            password.setError("Please provide strong password.");
        }else if (password.getText().toString().length()<8){
            password.setError("Must contain more than 8 characters.");
        }
        
    }
}