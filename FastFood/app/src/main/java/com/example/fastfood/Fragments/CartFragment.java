package com.example.fastfood.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.Adapters.CartAdapter;
import com.example.fastfood.Adapters.CatAdapter;
import com.example.fastfood.Adapters.SimpleVerticalAdapter;
import com.example.fastfood.EmailLoginRegister.EmailLoginActivity;
import com.example.fastfood.MainActivity;
import com.example.fastfood.Models.CartModel;
import com.example.fastfood.Models.CategoryModel;
import com.example.fastfood.Models.SimpleVerticalModel;
import com.example.fastfood.R;
import com.example.fastfood.Sessions.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartFragment extends Fragment implements View.OnClickListener {
    public CartFragment() {
    }
    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private RelativeLayout bookmarks,MonaGold;
    private TextView your_orders,favourite_orders,your_address,online_ordering_help,rate_playstore,report_safety_emergency,send_feedback,tv_login,tv_logout;
    SessionManager sessionManager;
    NestedScrollView rcv_ct;
    RecyclerView recyclerViewCartfood;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;
    TextView total, tvnocart;
    Button chekout;
    Dialog dialog1;
    LinearLayout nocart;

    private DatabaseReference Detailschekout;
    private DatabaseReference Oder;
    private DatabaseReference infOder;
//    CheckBox check;
       String timestamp;
    FirebaseUser user;
    ProgressDialog pd;
    Integer tongtien = 0;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);


        sessionManager = new SessionManager(getContext());
        pd = new ProgressDialog(getContext());
        tvnocart = view.findViewById(R.id.textnocart);
        nocart = view.findViewById(R.id.nocart);
        rcv_ct = view.findViewById(R.id.rcv_ct);

        tv_logout = view.findViewById(R.id.tv_logout);
        total = view.findViewById(R.id.total);
        // onSetNavigationDrawerEvents();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
       timestamp = String.valueOf(System.currentTimeMillis());
        Detailschekout = FirebaseDatabase.getInstance().getReference("Detailschekout");
        Oder = FirebaseDatabase.getInstance().getReference("Oder");
        infOder = FirebaseDatabase.getInstance().getReference("infor_Oder");
        dialog1 = new Dialog(getContext());
        dialog1.setContentView(R.layout.modal_checkout);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog1.setCancelable(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.BottomsheetStyle;
        chekout = view.findViewById(R.id.check_product);
        chekout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(user != null){
                    dialog1.show();
                    TextView tv = dialog1.findViewById(R.id.gmail_user);
                    tv.setText(user.getEmail());
                }else {
                    Toast.makeText(getContext(), "Bạn cần phải đăng nhập ", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    Animatoo.animateSwipeLeft(getContext());
                }

            }
        });
        dialog1.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploadchekout();


            }
        });
        dialog1.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        onSetNavigationDrawerEvents(view);

    }

    private void init(View view) {

        recyclerViewCartfood = view.findViewById(R.id.recyclerViewCartfood);
        LinearLayoutManager layoutManagerCartfood = new LinearLayoutManager(getContext());
        layoutManagerCartfood.setOrientation(RecyclerView.VERTICAL);
        recyclerViewCartfood.setLayoutManager(layoutManagerCartfood);
        layoutManagerCartfood.setStackFromEnd(true);
        layoutManagerCartfood.setReverseLayout(true);
        cartModelList = new ArrayList<>();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && cartModelList == null){
            nocart.setVisibility(View.VISIBLE);
            rcv_ct.setVisibility(View.GONE);
            tvnocart.setText("Chưa có sản phẩm nào!");


        }else if (user != null ){

            nocart.setVisibility(View.GONE);

            Query pro = FirebaseDatabase.getInstance().getReference("Carts").child(user.getUid());
            pro.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartModelList.clear();


                    for (DataSnapshot ds : snapshot.getChildren()) {
                        CartModel model = ds.getValue(CartModel.class);
                        String pr = ""+ds.child("tv_price").getValue();
                        cartModelList.add(model);
                        tongtien += Integer.parseInt(pr);
                        cartAdapter = new CartAdapter( cartModelList,getContext());
                        recyclerViewCartfood.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }
                    total.setText(Integer.toString(tongtien)+ " "+ "VNĐ");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if (user == null){
            nocart.setVisibility(View.VISIBLE);
            rcv_ct.setVisibility(View.GONE);
            tvnocart.setText("Bạn chưa đăng nhập!");
        }


    }
    private void Uploadchekout() {
        pd.setMessage("Đang thanh toán");
        pd.show();
        EditText diachi = dialog1.findViewById(R.id.diachi);
        EditText phone = dialog1.findViewById(R.id.phone);
        String dc = diachi.getText().toString().trim();
        String p = phone.getText().toString().trim();
        Query pro = FirebaseDatabase.getInstance().getReference("Carts").child(user.getUid());
        pro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CartModel model = ds.getValue(CartModel.class);
                    String id_cart = ""+ds.child("id_cart").getValue();
                    String check = ""+ds.child("check").getValue();
                    if (check.equals("true")){
                        Detailschekout.child(timestamp).child(id_cart).setValue(model);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("id_inf", timestamp);
        hashMap1.put("id_user", user.getUid());
        hashMap1.put("email", user.getEmail());
        hashMap1.put("phone", p);
        hashMap1.put("total", tongtien);
        hashMap1.put("status", "Mới");
        hashMap1.put("diachi", dc);
        infOder.child(timestamp).setValue(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        diachi.setText("");
        phone.setText("");
        dialog1.dismiss();

    }

    private void onSetNavigationDrawerEvents(View view) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_logout = view.findViewById(R.id.tv_logout);
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        MonaGold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            tv_login.setText(user.getEmail());
        }else {
            tv_logout.setVisibility(View.GONE);
        }
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
            tv_logout.setVisibility(View.VISIBLE);
        }
        else {
            tv_logout.setVisibility(View.GONE);
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


