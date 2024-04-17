package com.example.aichathub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FeedbackFragment extends Fragment {
    Spinner spinner;
    EditText text;
    Button send_btn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("users_feedback");
    FirebaseAuth auth = FirebaseAuth.getInstance();
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
                }else {
                    //Toast.makeText(getContext(), "type: "+selected_type+"\n"+"msg: "+txt_msg, Toast.LENGTH_SHORT).show();
                    Log.i("onSend", "type: "+selected_type+"\n"+"msg: "+txt_msg);
                    uploadMsg(selected_type,txt_msg);
                }
            }
        });
        return v;
    }

    private void uploadMsg(String selectedType, String txtMsg) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());
       // Toast.makeText(getContext(), "Date: " + formattedDate, Toast.LENGTH_SHORT).show();
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Sending...");
        pd.show();
        ref.child(formattedDate).child(selectedType).child(auth.getCurrentUser().getUid()).setValue(txtMsg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Thank you", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    text.setText(null);
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.custom_dialog_success);
                        dialog.show();

                        TextView dialog_txt =dialog.findViewById(R.id.dialog_text);
                        TextView dialog_txt2 =dialog.findViewById(R.id.dialog_text2);
                        ImageView dialog_close = dialog.findViewById(R.id.CloseDialog);
                        Button dialog_btn = dialog.findViewById(R.id.dialog_button1);

                        dialog_txt2.setText("Feedback sent!");
                        dialog_txt.setText("It's means a lot Thank You");
                        dialog_close.setOnClickListener(v1 -> dialog.dismiss());
                        dialog_btn.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

}