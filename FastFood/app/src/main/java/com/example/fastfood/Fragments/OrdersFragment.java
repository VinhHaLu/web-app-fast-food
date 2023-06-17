package com.example.fastfood.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.Adapters.BannerAdapter;
import com.example.fastfood.Adapters.CatAdapter;
import com.example.fastfood.Adapters.GreatOffersAdapter;
import com.example.fastfood.Adapters.SimpleVerticalAdapter;
import com.example.fastfood.EmailLoginRegister.EmailLoginActivity;
import com.example.fastfood.MainActivity;
import com.example.fastfood.Models.BannerModel;
import com.example.fastfood.Models.CategoryModel;
import com.example.fastfood.Models.GreatOffersModel;
import com.example.fastfood.Models.SimpleVerticalModel;
import com.example.fastfood.R;
import com.example.fastfood.Sessions.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OrdersFragment extends Fragment implements View.OnClickListener {
//    FirebaseAuth mAuth;
    public OrdersFragment() {
    }
    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private DatabaseReference Category;

    private RelativeLayout bookmarks,MonaGold;
    private TextView your_orders,favourite_orders,your_address,online_ordering_help,rate_playstore,report_safety_emergency,send_feedback,tv_login,tv_logout;

    //category slide//
    RecyclerView recyclerViewCategory;
    CatAdapter catAdapter;
    List<CategoryModel> categoryModelList;
    //////
    ////banner slider///
    RecyclerView recyclerViewBanner;
    BannerAdapter bannerAdapter;
    List<BannerModel> bannerModelList;
    ///////////////////
    ////simpple vertical slider///////////////////
    //recyclerViewSimple
    RecyclerView recyclerViewSimple;
    SimpleVerticalAdapter simpleVerticalAdapter;
    List<SimpleVerticalModel> simpleVerticalModelList;

    /////////////////////////////////////////

    ///////////////great offers horizontal//////////////////////
    RecyclerView recyclerViewGreatoffer;
    GreatOffersAdapter greatOffersAdapter;
    List<SimpleVerticalModel> simpleVerticalModelList2;
    ///////////////////////////////////////////////////////////

    SessionManager sessionManager;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_orders, container, false);
        sessionManager = new SessionManager(getContext());
        // onSetNavigationDrawerEvents();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

        ////// Category list /////////////////////////////////
        categoryModelList = new ArrayList<>();
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManager);

        Query Category = FirebaseDatabase.getInstance().getReference("CATEGORY");
        Category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CategoryModel model = ds.getValue(CategoryModel.class);
                    categoryModelList.add(model);

                    catAdapter = new CatAdapter(categoryModelList,getContext());
                    recyclerViewCategory.setAdapter(catAdapter);
                    catAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ////// End Category/////////////////////////////////


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            init(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onSetNavigationDrawerEvents(view);
    }

    private void init(View view) throws IOException {


/*        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://807979fed6da.ngrok.io/api/categories.php")
                .get()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("key", "whatever")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "whatever")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body());*/


        ////// Banner list start /////////////////////////////////
        recyclerViewBanner = view.findViewById(R.id.recyclerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        bannerModelList = new ArrayList<>();
        bannerModelList.add(new BannerModel(R.drawable.banner1));
        bannerModelList.add(new BannerModel(R.drawable.banner2));
        bannerModelList.add(new BannerModel(R.drawable.banner3));

        bannerAdapter = new BannerAdapter(bannerModelList,getContext());
        recyclerViewBanner.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        ////// Banner list end /////////////////////////////////


        /////////simple vertical list start//////////////////////////////////////////////////

        recyclerViewSimple = view.findViewById(R.id.recyclerViewSimple);
        LinearLayoutManager layoutManagerSimpleVertical = new LinearLayoutManager(getContext());
        layoutManagerSimpleVertical.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSimple.setLayoutManager(layoutManagerSimpleVertical);

        simpleVerticalModelList = new ArrayList<>();


        Query pro = FirebaseDatabase.getInstance().getReference("products");
        pro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                simpleVerticalModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SimpleVerticalModel model = ds.getValue(SimpleVerticalModel.class);
                    simpleVerticalModelList.add(model);

                    simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                    recyclerViewSimple.setAdapter(simpleVerticalAdapter);
                    simpleVerticalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.h10,"delicious food","fastfood","50%","2.3"));
//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.three,"delicious food","fastfood","50%","4.5"));
//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.h7,"delicious food","fastfood","50%","5.0"));
//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.h9,"delicious food","fastfood","50%","4.2"));
//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.two,"delicious food","fastfood","50%","1.3"));

        /////////simple vertical list end//////////////////////////////////////////////////

        ///////////////////////Great Offers Shop//////////////////////////////////
        simpleVerticalModelList2 = new ArrayList<>();
        recyclerViewGreatoffer = view.findViewById(R.id.recyclerViewGreatoffer);
        LinearLayoutManager layoutManagerGreatOffres = new LinearLayoutManager(getContext());
        layoutManagerGreatOffres.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewGreatoffer.setLayoutManager(layoutManagerGreatOffres);


//        Query pro2 = FirebaseDatabase.getInstance().getReference("Product");
//        pro2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                simpleVerticalModelList2.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    SimpleVerticalModel model = ds.getValue(SimpleVerticalModel.class);
//                    if (model.getTv_ratting().equals("5") || model.getTv_ratting().equals("4.5")){
//                        simpleVerticalModelList2.add(model);
//                    }
//
//                    greatOffersAdapter = new GreatOffersAdapter(simpleVerticalModelList2,getContext());
//                    recyclerViewGreatoffer.setAdapter(greatOffersAdapter);
//                    greatOffersAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //////////////////////////////////////////////////////////////////////////


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
}


