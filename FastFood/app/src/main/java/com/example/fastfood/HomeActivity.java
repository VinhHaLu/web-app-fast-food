package com.example.fastfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.Fragments.GoOutFragment;
import com.example.fastfood.Fragments.GoldFragment;
import com.example.fastfood.Fragments.OrdersFragment;
import com.example.fastfood.Fragments.CartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(HomeActivity.this,R.color.white));// set status background white
        Fragment selectedFragment = new OrdersFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.orders:
                           selectedFragment = new OrdersFragment();
                            break;

                        case R.id.goout:
                            selectedFragment = new GoOutFragment();
                            break;

                        case R.id.gold:
                            selectedFragment = new GoldFragment();
                            break;

                        case R.id.video:
                                selectedFragment = new CartFragment();
                            break;
                    }
                    /////////////replacing by default fragment on home activity////////////////
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment).commit();

                    return true;
                }
            };
}