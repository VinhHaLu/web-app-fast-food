package com.example.fastfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Buy_Activity extends AppCompatActivity {
 private ImageView back, imgdt;
    TextView value, tvtt, rate;
    TextView price;
    int count= 1;
    Button btn_buy;
    String idpro, img, title, ratting;
    String pricer;
    int sum;
    String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Window window = this.getWindow();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        idpro = intent.getStringExtra("id");
        value  = (TextView) findViewById(R.id.tv_count);
        price = (TextView) findViewById(R.id.tv_price);
        imgdt = findViewById(R.id.img_detail);
        tvtt = findViewById(R.id.tv_title);
        rate = findViewById(R.id.rate);
        btn_buy = findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buy();
            }
        });




        Query query = FirebaseDatabase.getInstance().getReference("products").orderByChild("id_product").equalTo(idpro);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                     title = ""+ ds.child("name_product").getValue();
                     img = ""+ ds.child("image_product").getValue();
                     pricer = ""+ ds.child("price_product").getValue();

//                      String cuy = Integer.toString();

                    price.setText("$" + pricer);
                    tvtt.setText(title);



                    try {
                        Picasso.get().load(img).into(imgdt);
                    }catch (Exception e){

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








       window.setStatusBarColor(Color.parseColor("#fd8700"));
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    public void increment(View view) {
            count++;
            value.setText(""+count);
            sum = count * Integer.parseInt(pricer);
        price.setText(Integer.toString(sum) + "VND" );
    }
    public void decrement(View view) {
            if (count<0)
                count=1;
            else count--;
            value.setText(""+count);
            sum = count * Integer.parseInt(pricer);
            price.setText(Integer.toString(sum) + "VND" );
    }
    private void Buy() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                if (count == 1){
                    sum = Integer.parseInt(pricer);
                }
                String timestamp = String.valueOf(System.currentTimeMillis());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id_cart", timestamp);
                hashMap.put("img_cart", img);
                hashMap.put("number_cart", count);
                hashMap.put("title_cart", title);
                hashMap.put("totalPrice", sum);
                hashMap.put("tv_price", sum);
                hashMap.put("check", "false");
                DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Carts");
                ref3.child(myUid).child(timestamp).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Buy_Activity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Buy_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(Buy_Activity.this, "Bạn cần phải đăng nhập ", Toast.LENGTH_SHORT).show();
            }



    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}