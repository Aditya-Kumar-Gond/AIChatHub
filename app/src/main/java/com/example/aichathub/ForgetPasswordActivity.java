package com.example.aichathub;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText email;
    Button forget_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        forget_btn = findViewById(R.id.button2);
        email = findViewById(R.id.EmailId);

        forget_btn.setOnClickListener(v->{
            String txt_email = email.getText().toString();
            if(txt_email.isEmpty()){
                Toast.makeText(this, "please provide valid email", Toast.LENGTH_SHORT).show();
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(txt_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this, "Please Check your mail", Toast.LENGTH_SHORT).show();
                            Dialog dialog = new Dialog(ForgetPasswordActivity.this);
                            dialog.setContentView(R.layout.custom_dialog_success);
                            dialog.show();

                            TextView dialog_txt =dialog.findViewById(R.id.dialog_text);
                            TextView dialog_txt2 =dialog.findViewById(R.id.dialog_text2);
                            ImageView dialog_close = dialog.findViewById(R.id.CloseDialog);
                            Button dialog_btn = dialog.findViewById(R.id.dialog_button1);

                            dialog_txt2.setText("Please check your mail!");
                            dialog_txt.setText("We have sent an email to reset your password");
                            dialog_close.setOnClickListener(v1 -> dialog.dismiss());
                            dialog_btn.setOnClickListener(v1 -> {
                                dialog.dismiss();
                                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivityActivity.class));
                                finish();
                            });
                        }else{
                            Toast.makeText(ForgetPasswordActivity.this, "Please Provide your correct Mail!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}