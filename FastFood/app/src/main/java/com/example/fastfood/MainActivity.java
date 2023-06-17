package com.example.fastfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.Adapters.PlateAdapter;
import com.example.fastfood.EmailLoginRegister.EmailLoginActivity;
import com.example.fastfood.Models.PlateModel;
import com.example.fastfood.PhoneLoginRegister.PhoneLoginActivity;
import com.example.fastfood.Sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PlateModel> plateModelList;
    private PlateAdapter plateAdapter;
    private LinearLayout emailContinue,phoneContinue;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        ///////App update checker start/////////
//       try {
//            AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this);
//            appUpdateChecker.checkForUpdate(false);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        ///////////////////////App update checker end//////////



        /////////////status bar hide start ///////////////
//       getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /////////////status bar hide end ///////////////

        emailContinue = (LinearLayout) findViewById(R.id.linear2);
        phoneContinue = (LinearLayout) findViewById(R.id.linear1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModelList = new ArrayList<>();
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.two));
        plateModelList.add(new PlateModel(R.drawable.three));
        plateModelList.add(new PlateModel(R.drawable.four));
        plateModelList.add(new PlateModel(R.drawable.five));
        plateAdapter = new PlateAdapter(plateModelList,this);
        recyclerView.setAdapter(plateAdapter);
        plateAdapter.notifyDataSetChanged();
        autoScroll();

        /////////////continue with email///////////////
        emailContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmailLoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(MainActivity.this);

            }
        });

    ////////////////////////////////////////////////////////////
    /////////////continue with phone///////////////
     phoneContinue.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(MainActivity.this);

        }
    });
}
    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == plateAdapter.getItemCount())
                    count = 0;
                if (count< plateAdapter.getItemCount()){
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);

                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    public void gotoHomePage(View view) {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sessionManager.isLogin())
        {
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
            Animatoo.animateSlideLeft(this);
        }
        else
        {

        }
    }
}