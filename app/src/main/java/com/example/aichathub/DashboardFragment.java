package com.example.aichathub;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DashboardFragment extends Fragment {

    RecyclerView rv;
    TextView username;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users_detail");
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rv = view.findViewById(R.id.rv);
        username = view.findViewById(R.id.textView2);

        username.setText(DatabaseClass.user_fullname);
        DataAdapter adapter = new DataAdapter();
      //  StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(view.getContext(),)
        GridLayoutManager manager = new GridLayoutManager(view.getContext(),2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);



        return view;
    }

    private void fetchData() {
        String uid = auth.getUid();
        assert uid != null;
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name_txt = snapshot.child("Full_name").getValue(String.class);

                    username.setText(name_txt);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}