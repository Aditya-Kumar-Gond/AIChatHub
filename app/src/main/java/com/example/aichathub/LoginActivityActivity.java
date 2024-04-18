package com.example.aichathub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivityActivity extends AppCompatActivity {
EditText email,pwd;
Button login_btn;
TextView forget_pwd,register;
FirebaseAuth auth = FirebaseAuth.getInstance();
CardView google_login,fb_login,x_login;
FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference ref = database.getReference().child("users_detail");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        forget_pwd = findViewById(R.id.ForgetPassword);
        register = findViewById(R.id.toSignup);
        google_login = findViewById(R.id.google_login);
        fb_login = findViewById(R.id.facebook_login);
        x_login = findViewById(R.id.x_login);

        login_btn.setOnClickListener(v->{
            String user_email = email.getText().toString();
            String password = pwd.getText().toString();
            if(user_email.isEmpty()){
                email.setError("Cannot be empty!");
            } else if (password.isEmpty()) {
                pwd.setError("cannot be empty!");
            }else {
                login_user(user_email,password);
            }
        });

        register.setOnClickListener(v2->{
            startActivity(new Intent(this,SignUpActivity.class));
        });

        forget_pwd.setOnClickListener(v3->{
            startActivity(new Intent(this, ForgetPasswordActivity.class));
            Toast.makeText(this, "soon.....", Toast.LENGTH_SHORT).show();
        });
    }

    private void login_user(String user_email, String password) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        auth.signInWithEmailAndPassword(user_email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                dialog.dismiss();
                finish();
                ref.child(authResult.getUser().getUid()).child("Password").setValue(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(LoginActivityActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivityActivity.this,MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivityActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                email.setText(null);
                pwd.setText(null);
                Toast.makeText(LoginActivityActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

    }
}