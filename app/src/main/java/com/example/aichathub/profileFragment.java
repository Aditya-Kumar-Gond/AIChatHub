package com.example.aichathub;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {
EditText editText;
CircleImageView edit_profile_img,profile_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        edit_profile_img = view.findViewById(R.id.edit_profile_image);
        profile_img = view.findViewById(R.id.profile_image);

        edit_profile_img.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,100);
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == getActivity().RESULT_OK){
            Toast.makeText(getContext(), "worked", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            profile_img.setImageURI(uri);
        }
    }
}