package com.example.aichathub;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    NavigationView navi;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    CircleImageView profile_img_toolbar,profile_img_header;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Uri image_uri = null;

    LinearLayout home,setting,refer,feedback,logout;
    TextView txt_header,txt_header_feedback,home_tv,setting_tv,refer_tv,feedback_tv,username,email, email_nav,name_nav;
    ImageView home_icon,setting_icon,refer_icon,feedback_icon,navigation_icon,profile_bottom_icon,feedback_bottom_icon,dashBoard_bottom_icon;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("users_detail");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference().child("users_profile_image");

    SharedPreferences preferences;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navi = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation_icon = findViewById(R.id.navi_icon);
        toolbar = findViewById(R.id.toolbar);
        feedback_bottom_icon = findViewById(R.id.feedback);
        dashBoard_bottom_icon = findViewById(R.id.dashboard);
        profile_bottom_icon = findViewById(R.id.profile);
        txt_header = findViewById(R.id.topActivity_header);
        profile_img_toolbar = findViewById(R.id.circleProfile_image);
        txt_header_feedback = findViewById(R.id.header2);

        navigationHeader(navi);
        fetchData();
        preferences = getSharedPreferences("user_data",MODE_PRIVATE);

        navigation_icon.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);
        });

        profile_bottom_icon.setOnClickListener(v->{
            profileFragment fragment = new profileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img_toolbar.setVisibility(View.GONE);
            txt_header.setVisibility(View.VISIBLE);
            txt_header_feedback.setVisibility(View.GONE);
            profile_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            dashBoard_bottom_icon.setBackground(null);
            feedback_bottom_icon.setBackground(null);
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
         //   toolbar.setBackground(getDrawable(R.drawable.custom_design));
        });

        dashBoard_bottom_icon.setOnClickListener(v -> {
            DashboardFragment dashboardFragment = new DashboardFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment,dashboardFragment)
                    .commit();
            profile_img_toolbar.setVisibility(View.VISIBLE);
            txt_header.setVisibility(View.GONE);
            txt_header_feedback.setVisibility(View.VISIBLE);
            txt_header_feedback.setText("Dashboard");
            //     toolbar.setBackground(getDrawable(R.color.white));
            dashBoard_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            profile_bottom_icon.setBackground(null);
            feedback_bottom_icon.setBackground(null);
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
        });

        feedback_bottom_icon.setOnClickListener(v -> {
            FeedbackFragment Fragment = new FeedbackFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment,Fragment)
                    .commit();

            profile_img_toolbar.setVisibility(View.VISIBLE);
            txt_header.setVisibility(View.GONE);
            txt_header_feedback.setVisibility(View.VISIBLE);
            txt_header_feedback.setText("Feedback");
            //     toolbar.setBackground(getDrawable(R.color.white));
            feedback_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            dashBoard_bottom_icon.setBackground(null);
            profile_bottom_icon.setBackground(null);
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
        });
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void navigationHeader(ViewGroup viewGroup) {

        View view = LayoutInflater.from(this).inflate(R.layout.nav_header,viewGroup,false);
        navi.addHeaderView(view);

        Button edit_btn;
        home = view.findViewById(R.id.home_header);
        setting = view.findViewById(R.id.setting_header);
        refer = view.findViewById(R.id.refer_header);
        feedback = view.findViewById(R.id.support_header);
        logout = view.findViewById(R.id.logout_header);
        home_tv = view.findViewById(R.id.home_text);
        setting_tv = view.findViewById(R.id.setting_text);
        refer_tv = view.findViewById(R.id.refer_text);
        feedback_tv = view.findViewById(R.id.feedback_text);
        home_icon = view.findViewById(R.id.home_icon);
        setting_icon = view.findViewById(R.id.setting_icon);
        refer_icon = view.findViewById(R.id.refer_icon);
        feedback_icon = view.findViewById(R.id.phone_icon);

        username = view.findViewById(R.id.username_header);
        email = view.findViewById(R.id.user_email_header);
        edit_btn = view.findViewById(R.id.edit_profile_btn_header);
        profile_img_header = view.findViewById(R.id.user_image_header);


        feedback.setOnClickListener(v->{
            setAppearanceOfHolder();
            v.setBackground(getResources().getDrawable(R.drawable.purple_bg_holder));
            feedback_tv.setTextColor(WHITE);
            feedback_icon.setColorFilter(WHITE);

            FeedbackFragment fragment = new FeedbackFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img_toolbar.setVisibility(View.VISIBLE);
            txt_header.setVisibility(View.GONE);
            txt_header_feedback.setVisibility(View.VISIBLE);
            txt_header_feedback.setText("Feedback");
            feedback_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            dashBoard_bottom_icon.setBackground(null);
            profile_bottom_icon.setBackground(null);
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            drawerLayout.close();
        });

        logout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(this,LoginActivityActivity.class));
            Toast.makeText(MainActivity.this,"logout",Toast.LENGTH_SHORT).show();
        });

        edit_btn.setOnClickListener(v->{
            drawerLayout.close();
            profileFragment fragment = new profileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img_toolbar.setVisibility(View.GONE);
            txt_header.setVisibility(View.VISIBLE);
            txt_header_feedback.setVisibility(View.GONE);
            profile_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            dashBoard_bottom_icon.setBackground(null);
            feedback_bottom_icon.setBackground(null);
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
        });

        home.setOnClickListener(v->{
            setAppearanceOfHolder();
            v.setBackground(getResources().getDrawable(R.drawable.purple_bg_holder));
            home_tv.setTextColor(WHITE);
            home_icon.setColorFilter(WHITE);
            drawerLayout.close();
            DashboardFragment fragment = new DashboardFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img_toolbar.setVisibility(View.VISIBLE);
            txt_header.setVisibility(View.GONE);
            txt_header_feedback.setVisibility(View.VISIBLE);
            txt_header_feedback.setText("Dashboard");
            dashBoard_bottom_icon.setBackground(getDrawable(R.drawable.bottom_selection_bg));
            feedback_bottom_icon.setBackground(null);
            profile_bottom_icon.setBackground(null);
            dashBoard_bottom_icon.setColorFilter(getResources().getColor(R.color.white));
            feedback_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
            profile_bottom_icon.setColorFilter(getResources().getColor(R.color.purple_dark));
        });

    }



    private void fetchData() {
        ProgressDialog dialog = new ProgressDialog(this);
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

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_name",name_txt);
                    editor.putString("user_age",age_txt);
                    editor.putString("user_email",email_txt);
                    editor.putString("user_gender",gender_txt);
                    editor.apply();

                    username.setText(name_txt);
                    email.setText(email_txt);
             //       email_nav.setText(email_txt);

                    storageReference.child(auth.getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_uri = uri;
                            String image  = String.valueOf(uri);
                            editor.putString("image_uri",image);
                            editor.apply();
                            Picasso.get().load(uri).into(profile_img_toolbar);
                            Picasso.get().load(image_uri).into(profile_img_header);
                            dialog.dismiss();
                        }
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    });
                }else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "failed to Fetch data", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
    }




    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void setAppearanceOfHolder() {
        home.setBackground(null);
        setting.setBackground(null);
        refer.setBackground(null);
        feedback.setBackground(null);
        logout.setBackground(null);

        home_tv.setTextColor(Color.BLACK);
        setting_tv.setTextColor(Color.BLACK);
        refer_tv.setTextColor(Color.BLACK);
        feedback_tv.setTextColor(Color.BLACK);

        home_icon.setColorFilter(Color.BLACK);
        setting_icon.setColorFilter(Color.BLACK);
        refer_icon.setColorFilter(Color.BLACK);
        refer_icon.setColorFilter(Color.BLACK);
        feedback_icon.setColorFilter(BLACK);
    }
}