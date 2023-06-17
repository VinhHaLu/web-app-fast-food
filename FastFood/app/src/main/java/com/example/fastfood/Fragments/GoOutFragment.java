package com.example.fastfood.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.EmailLoginRegister.EmailLoginActivity;
import com.example.fastfood.MainActivity;
import com.example.fastfood.R;
import com.example.fastfood.Sessions.SessionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;


public class GoOutFragment extends Fragment implements OnMapReadyCallback ,View.OnClickListener {
    public GoOutFragment() {
    }
    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private RelativeLayout bookmarks,MonaGold;
    private TextView your_orders,favourite_orders,your_address,online_ordering_help,rate_playstore,report_safety_emergency,send_feedback,tv_login,tv_logout;
    SessionManager sessionManager;
    ///google map///
    GoogleMap map;
    MapView mapView;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_go_out, container, false);
        sessionManager = new SessionManager(getContext());

        ///obtain the supportmapfragment///////



        ///////////////////////////
        // onSetNavigationDrawerEvents();
        return view;
    }



    private void onSetNavigationDrawerEvents(View view) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_logout = view.findViewById(R.id.tv_logout);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            tv_login.setText(user.getEmail());
        }else {
            tv_logout.setVisibility(View.GONE);
        }
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        MonaGold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);

        your_orders = (TextView) view.findViewById(R.id.your_orders);
        favourite_orders = (TextView) view.findViewById(R.id.favourite_orders);
        your_address = (TextView) view.findViewById(R.id.your_address);
        online_ordering_help = (TextView) view.findViewById(R.id.online_ordering_help);
        rate_playstore = (TextView) view.findViewById(R.id.rate_playstore);
        report_safety_emergency = (TextView) view.findViewById(R.id.report_safety_emergency);
        send_feedback = (TextView) view.findViewById(R.id.send_feedback);


        tv_logout.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        bookmarks.setOnClickListener(this);
        MonaGold.setOnClickListener(this);

        navigationBar.setOnClickListener(this);
        your_orders.setOnClickListener(this);
        favourite_orders.setOnClickListener(this);
        your_address.setOnClickListener(this);
        online_ordering_help.setOnClickListener(this);
        rate_playstore.setOnClickListener(this);
        report_safety_emergency.setOnClickListener(this);
        send_feedback.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.tv_login:
                Login();
                break;
            case R.id.tv_logout:
                Logout();
                break;
            case R.id.relativeLayout3:
                Toast.makeText(getContext(), "bookmarks", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout4:
                Toast.makeText(getContext(), "MonaGold", Toast.LENGTH_SHORT).show();
                break;
            case R.id.your_orders:
                Toast.makeText(getContext(), "your_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.your_address:
                Toast.makeText(getContext(), "your_address", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favourite_orders:
                Toast.makeText(getContext(), "favourite_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Toast.makeText(getContext(), "send_feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_safety_emergency:
                Toast.makeText(getContext(), "report_safety_emergency", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate_playstore:
                Toast.makeText(getContext(), "rate_playstore", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Login() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Toast.makeText(getContext(), "Bạn đã đăng nhập ", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent =  new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
            Animatoo.animateSwipeLeft(getContext());
        }

    }

    private void Logout() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(
                user != null

        ){
            mAuth.signOut();
            Intent intent =  new Intent(getContext(), EmailLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            Animatoo.animateSwipeLeft(getContext());
        }


    }

    @Override
    public void onStart() {
        super.onStart();

//        if (sessionManager.isLogin())
//        {
//            tv_login.setVisibility(View.GONE);
//            tv_logout.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
         map = googleMap;

    }
}


