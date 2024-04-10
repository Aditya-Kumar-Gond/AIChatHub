package com.example.aichathub;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    NavigationView navi;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView txt_header,txt_header_feedback;
    CircleImageView profile_img;
    ImageView navigation_icon,profile_bottom_icon,feedback_bottom_icon,dashBoard_bottom_icon;
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
        profile_img = findViewById(R.id.circleProfile_image);
        txt_header_feedback = findViewById(R.id.header2);

        navigationHeader(navi);
        navigation_icon.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);
        });


        profile_bottom_icon.setOnClickListener(v->{
            profileFragment fragment = new profileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img.setVisibility(View.GONE);
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
            profile_img.setVisibility(View.VISIBLE);
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

            profile_img.setVisibility(View.VISIBLE);
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
    LinearLayout home,setting,refer,feedback,logout;
    TextView home_tv,setting_tv,refer_tv,feedback_tv;
    ImageView home_icon,setting_icon,refer_icon,feedback_icon;
    TextView username,email;
    @SuppressLint("UseCompatLoadingForDrawables")
    private void navigationHeader(ViewGroup viewGroup) {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header,viewGroup,false);
        navi.addHeaderView(view);

        Button edit_btn;
        ImageView profile_img_header;
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
            profile_img.setVisibility(View.VISIBLE);
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
            Toast.makeText(MainActivity.this,"logout",Toast.LENGTH_SHORT).show();
        });

        edit_btn.setOnClickListener(v->{
            drawerLayout.close();
            profileFragment fragment = new profileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment,fragment).commit();
            profile_img.setVisibility(View.GONE);
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
            profile_img.setVisibility(View.VISIBLE);
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