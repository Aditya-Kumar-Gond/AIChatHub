package com.example.aichathub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {
EditText name,age,email;
Button update_btn;
TextView name_tv,email_tv;
Uri image_uri = null;
RadioGroup radioGroup;
RadioButton male_btn,female_btn,other_btn;
FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference().child("users_profile_image");

    View view;
DatabaseReference ref = database.getReference().child("users_detail");
FirebaseAuth auth = FirebaseAuth.getInstance();
CircleImageView edit_profile_img,profile_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        hooks();
        fetchData();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        edit_profile_img.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent,"user_image"),100);
        });
        return view;
    }

    private void uploadData() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Updating...");
        dialog.show();
        String fullname = name.getText().toString();
        String age_txt = age.getText().toString();
        String email_txt = email.getText().toString();
        String gender = null;
        if(male_btn.isChecked()){
            gender = "male";
        } else if (female_btn.isChecked()) {
            gender = "female";
        }else {
            gender = "other";
        }

        if(fullname.isEmpty() || age_txt.isEmpty() || radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(getContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String> data = new HashMap<>();
            data.put("Full_name",fullname);
            data.put("Age",age_txt);
            data.put("Gender",gender);
            data.put("Email",email_txt);

            ref.child(auth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        fetchData();
                        dialog.dismiss();
                       // Navigation.findNavController(getContext(),R.id.mobile_navigation).navigate(R.id.action_navigation_profile_to_navigation_dashboard);
                    }else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void hooks() {
        edit_profile_img = view.findViewById(R.id.edit_profile_image);
        profile_img = view.findViewById(R.id.profile_image);
        male_btn = view.findViewById(R.id.radio_male);
        female_btn = view.findViewById(R.id.radio_female);
        other_btn = view.findViewById(R.id.radio_other);
        radioGroup = view.findViewById(R.id.radio_group);
        name_tv = view.findViewById(R.id.user_id);
        email_tv = view.findViewById(R.id.user_email);
        name = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.et_email);
        age = view.findViewById(R.id.age);
        update_btn  = view.findViewById(R.id.update_btn);
    }

    private void fetchData() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Fetching data...");
        dialog.show();
        String uid = auth.getUid();
        assert uid != null;
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name_txt = snapshot.child("Full_name").getValue(String.class);
                    String age_txt = snapshot.child("Age").getValue(String.class);
                    String email_txt = snapshot.child("Email").getValue(String.class);
                    String gender_txt = snapshot.child("Gender").getValue(String.class);

                    name.setText(name_txt);
                    name_tv.setText(name_txt);
                    age.setText(age_txt);
                    email.setText(email_txt);
                    email_tv.setText(email_txt);
                    if(gender_txt!=null){
                        if(gender_txt.equals("male")){
                            male_btn.toggle();
                        }else if (gender_txt.equals("female")){
                            female_btn.toggle();
                        }else {
                            other_btn.toggle();
                        }
                    }

                    storageReference.child(auth.getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_uri = uri;
                            Picasso.get().load(uri).into(profile_img);
                            dialog.dismiss();
                        }
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(getContext(), "failed to Fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == getActivity().RESULT_OK){
          //  Toast.makeText(getContext(), "worked", Toast.LENGTH_SHORT).show();
            image_uri = data.getData();
            uploadImage();
            profile_img.setImageURI(image_uri);
        }
    }

    private void uploadImage() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("uploading...");
        dialog.show();
         storageReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).putFile(image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}